package org.sdmxsource.sdmx.dataparser.model;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.MappingJsonFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.sdmxsource.sdmx.api.exception.SdmxException;
import org.sdmxsource.sdmx.api.util.ReadableDataLocation;
import org.sdmxsource.sdmx.util.date.DateUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

/**
 * The type Json reader.
 */
public class JsonReader {
    /**
     * The constant SKIP_OBJECT_ITERATOR.
     */
    public static final SkipObjectIterator SKIP_OBJECT_ITERATOR = new JsonReader().new SkipObjectIterator();
    private static final Logger LOG = LogManager.getLogger(JsonReader.class);
    private ReadableDataLocation rdl;
    private JsonParser jParser;
    private JsonToken token;

    private Stack<JsonStackItem> stack;
    private Stack<Iterator> iteratorStack;
    private boolean iterating = false;
    private JsonStackItem currentStackItem;
    private String fieldName;

    private JsonReader() {
    }

    /**
     * Instantiates a new Json reader.
     *
     * @param rdl the rdl
     */
    public JsonReader(ReadableDataLocation rdl) {
        this.rdl = rdl;
        reset();
    }

    /**
     * Close.
     */
    public void close() {
        closeParser();
        rdl.close();
    }

    /**
     * Close parser.
     */
    public void closeParser() {
        try {
            jParser.close();
        } catch (IOException e) {
            LOG.warn("Error attempting to close JsonParser", e);
        }
    }

    /**
     * Reset.
     */
    public void reset() {
        LOG.debug("Reset");
        token = null;
        stack = new Stack<JsonStackItem>();
        currentStackItem = null;
        iteratorStack = new Stack<Iterator>();
        JsonFactory jfactory = new MappingJsonFactory();
        if (jParser != null) {
            try {
                jParser.close();
            } catch (IOException e) {

            }
        }
        try {
            jParser = jfactory.createParser(rdl.getInputStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Iterate.
     *
     * @param it the it
     */
    public void iterate(Iterator it) {
        LOG.debug("iterate");
        iterating = true;
        iteratorStack = new Stack<Iterator>();
        while (iterating == true && it != null && moveNext()) {
            if (isEndObject() || isEndArray()) {
                String fieldName = currentStackItem != null ? currentStackItem.getFieldName() : null;
                it.end(fieldName, isEndObject());
                it = iteratorStack.pop();
            } else if (isStartObject() || isStartArray()) {
                String fieldName = currentStackItem != null ? currentStackItem.getFieldName() : null;
                Iterator subObjectIt = it.start(fieldName, isStartObject());
                iteratorStack.push(it);  //Store the old iterator
                if (subObjectIt != null) {
                    it = subObjectIt;
                }
            } else {
                it.next(fieldName);
            }
        }
    }

    /**
     * Stop iterating.
     */
    public void stopIterating() {
        iterating = false;
    }

    /**
     * Move next start object boolean.
     *
     * @return the boolean
     */
    public boolean moveNextStartObject() {
        while (moveNext()) {
            if (isStartObject()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Move next start array boolean.
     *
     * @return the boolean
     */
    public boolean moveNextStartArray() {
        while (moveNext()) {
            if (isStartArray()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is start object boolean.
     *
     * @return the boolean
     */
    public boolean isStartObject() {
        return token == JsonToken.START_OBJECT;
    }

    /**
     * Is end object boolean.
     *
     * @return the boolean
     */
    public boolean isEndObject() {
        return token == JsonToken.END_OBJECT;
    }

    /**
     * Is start array boolean.
     *
     * @return the boolean
     */
    public boolean isStartArray() {
        return token == JsonToken.START_ARRAY;
    }

    /**
     * Is end array boolean.
     *
     * @return the boolean
     */
    public boolean isEndArray() {
        return token == JsonToken.END_ARRAY;
    }

    /**
     * Gets current field name.
     *
     * @return the current field name
     */
    public String getCurrentFieldName() {
        return fieldName;
    }

    /**
     * Gets value as string.
     *
     * @return the value as string
     */
    public String getValueAsString() {
        try {
            return jParser.getValueAsString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets value as date.
     *
     * @return the value as date
     */
    public Date getValueAsDate() {
        try {
            return DateUtil.formatDate(jParser.getValueAsString(), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets value as boolean.
     *
     * @return the value as boolean
     */
    public Boolean getValueAsBoolean() {
        try {
            return jParser.getValueAsBoolean();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Gets token.
     *
     * @return the token
     */
    public JsonToken getToken() {
        return token;
    }

    /**
     * Read string array list.
     *
     * @return the list
     */
    public List<String> readStringArray() {
        List<String> list = new ArrayList<String>();
        while (moveNext() && token != JsonToken.END_ARRAY) {
            try {
                list.add(jParser.getValueAsString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * Read integer array list.
     *
     * @return the list
     */
    public List<Integer> readIntegerArray() {
        List<Integer> list = new ArrayList<Integer>();
        while (moveNext() && token != JsonToken.END_ARRAY) {
            try {
                String asStr = jParser.getValueAsString();
                if (asStr == null || "null".equals(asStr)) {
                    list.add(null);
                } else {
                    list.add(Integer.parseInt(asStr));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return list;
    }

    /**
     * Returns true if the parent field has the name
     *
     * @param name the name
     * @return boolean boolean
     */
    public boolean isParentField(String name) {
        if (currentStackItem != null && currentStackItem.getParentItem() != null) {
            if (name == null) {
                if (currentStackItem.getParentItem().getFieldName() == null) {
                    return true;
                }
            } else if (name.equals(currentStackItem.getParentItem().getFieldName())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets current stack item.
     *
     * @return the current stack item
     */
    public JsonStackItem getCurrentStackItem() {
        return currentStackItem;
    }

    /**
     * Move next boolean.
     *
     * @return the boolean
     */
    public boolean moveNext() {
        JsonStackItem newStackItem = null;
        try {
            token = jParser.nextToken();
            if (token != null) {
                switch (token) {
                    case START_ARRAY:
                        //Root Item
                        //System.out.println("Start Array: "+jParser.getCurrentName());
                        newStackItem = new JsonStackItem(currentStackItem, null, STACK_TYPE.ARRAY);
                        stack.push(newStackItem);
                        break;
                    case START_OBJECT:
                        //Root Item
//					System.out.println("Start Object: "+jParser.getCurrentName());
                        newStackItem = new JsonStackItem(currentStackItem, null, STACK_TYPE.OBJECT);
                        stack.push(newStackItem);
                        break;
                    case FIELD_NAME:
                        fieldName = jParser.getCurrentName();
                        token = jParser.nextToken();
                        switch (token) {
                            case START_ARRAY:
//							System.out.println("Start Array: "+jParser.getCurrentName());
                                LOG.debug("Start Array: " + jParser.getCurrentName());
                                newStackItem = new JsonStackItem(currentStackItem, fieldName, STACK_TYPE.ARRAY);
                                stack.push(newStackItem);
                                break;
                            case START_OBJECT:
//							System.out.println("Start Object: "+jParser.getCurrentName());
                                LOG.debug("Start Object: " + jParser.getCurrentName());
                                newStackItem = new JsonStackItem(currentStackItem, fieldName, STACK_TYPE.OBJECT);
                                stack.push(newStackItem);
                                break;
                        }
                        break;
                    case END_ARRAY:
                    case END_OBJECT:
                        stack.pop();
                        if (stack.size() > 0) {
                            currentStackItem = stack.lastElement();
                        } else {
                            currentStackItem = null;
                        }
                        if (currentStackItem != null) {
//						System.out.println("End Object|Array: "+currentStackItem.getFieldName());
                            LOG.debug("End Object|Array: " + currentStackItem.getFieldName());
                        }
                }
            }
            if (newStackItem != null) {
                currentStackItem = stack.lastElement();
            }
            return token != null;
        } catch (JsonParseException e) {
            throw new SdmxException(e, "Error Reading JSON");
        } catch (IOException e) {
            throw new SdmxException(e, "Error Reading JSON");
        }
    }

    /**
     * The enum Stack type.
     */
    public enum STACK_TYPE {
        /**
         * Object stack type.
         */
        OBJECT,
        /**
         * Array stack type.
         */
        ARRAY;
    }

    /**
     * The interface Iterator.
     */
    public interface Iterator {

        /**
         * Next.
         *
         * @param fieldName the field name
         */
        void next(String fieldName);

        /**
         * End.
         *
         * @param fieldName the field name
         * @param isObject  the is object
         */
        void end(String fieldName, boolean isObject);

        /**
         * Start iterator.
         *
         * @param fieldName the field name
         * @param isObject  the is object
         * @return the iterator
         */
        Iterator start(String fieldName, boolean isObject);
    }

    /**
     * The type Json stack item.
     */
    public class JsonStackItem {
        private JsonStackItem parentItem;
        private String fieldName;
        private STACK_TYPE type;

        private JsonStackItem(JsonStackItem parentItem, String fieldName, STACK_TYPE type) {
            this.parentItem = parentItem;
            this.fieldName = fieldName;
            this.type = type;
        }

        /**
         * Gets parent item.
         *
         * @return the parent item
         */
        public JsonStackItem getParentItem() {
            return parentItem;
        }

        /**
         * Gets field name.
         *
         * @return the field name
         */
        public String getFieldName() {
            return fieldName;
        }

        /**
         * Gets type.
         *
         * @return the type
         */
        public STACK_TYPE getType() {
            return type;
        }

        /**
         * Is root boolean.
         *
         * @return the boolean
         */
        public boolean isRoot() {
            return parentItem == null;
        }

    }

    private class SkipObjectIterator implements Iterator {

        @Override
        public void next(String fieldName) {
        }

        @Override
        public void end(String fieldName, boolean isObject) {
        }

        @Override
        public Iterator start(String fieldName, boolean isObject) {
            return null;
        }
    }
}
