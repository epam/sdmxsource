package org.sdmxsource.sdmx.dataparser.engine.reader.json;

import org.sdmxsource.sdmx.api.model.beans.base.AnnotationBean;
import org.sdmxsource.sdmx.api.model.mutable.base.AnnotationMutableBean;
import org.sdmxsource.sdmx.dataparser.model.JsonReader;
import org.sdmxsource.sdmx.dataparser.model.JsonReader.Iterator;
import org.sdmxsource.sdmx.sdmxbeans.model.beans.base.AnnotationBeanImpl;
import org.sdmxsource.sdmx.sdmxbeans.model.mutable.base.AnnotationMutableBeanImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Annotations iterator.
 */
public class AnnotationsIterator extends AbstractIterator {
    private List<AnnotationBean> annotations = new ArrayList<AnnotationBean>();
    private AnnotationMutableBean annotation = null;

    /**
     * Instantiates a new Annotations iterator.
     *
     * @param jReader the j reader
     */
    public AnnotationsIterator(JsonReader jReader) {
        super(jReader);
    }

    @Override
    public void next(String fieldName) {
        if (annotation == null) {
            return;
        }
        if ("title".equals(fieldName)) {
            annotation.setTitle(jReader.getValueAsString());
        } else if ("type".equals(fieldName)) {
            annotation.setType(jReader.getValueAsString());
        } else if ("uri".equals(fieldName)) {
            annotation.setUri(jReader.getValueAsString());
        } else if ("text".equals(fieldName)) {
            annotation.addText("en", jReader.getValueAsString());
        } else if ("id".equals(fieldName)) {
            annotation.setId(jReader.getValueAsString());
        }
    }

    @Override
    public Iterator start(String fieldName, boolean isObject) {
        annotation = new AnnotationMutableBeanImpl();
        return this;
    }

    @Override
    public void end(String fieldName, boolean isObject) {
        if (annotation != null) {
            annotations.add(new AnnotationBeanImpl(annotation, null));
            annotation = null;
        }
    }

    /**
     * Gets annotations.
     *
     * @return the annotations
     */
    public List<AnnotationBean> getAnnotations() {
        return annotations;
    }
}
