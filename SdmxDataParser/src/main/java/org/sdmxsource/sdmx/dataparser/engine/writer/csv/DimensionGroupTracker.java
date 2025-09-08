package org.sdmxsource.sdmx.dataparser.engine.writer.csv;

import static org.sdmxsource.sdmx.dataparser.engine.writer.csv.GroupsProcessingState.GROUPS_NOT_STARTED;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.MapUtils;

public class DimensionGroupTracker {

    private final Map<String, DimensionGroup> dimensionGroupsByGroupKeys = new LinkedHashMap<>();
    private final List<GroupKeyCreator> groupKeyCreators = new ArrayList<>();
    private final List<DimensionGroup> currentSeriesDimensionGroups = new ArrayList<>();

    private GroupsProcessingState groupsProcessingState = GROUPS_NOT_STARTED;
    private DimensionGroup currentGroupInProgress;

    public void startGroup() {
        currentGroupInProgress = new DimensionGroup(new HashMap<>(), new HashMap<>());
    }

    public void findMatchingGroupAttributes(Map<String, String> dimensions) {
        for (GroupKeyCreator groupKeyCreator : this.groupKeyCreators) {
            if (!groupKeyCreator.matches(dimensions)) {
                continue;
            }
            String groupKey = groupKeyCreator.create(dimensions);
            DimensionGroup dimensionGroup = this.getGroupByKey(groupKey);
            if (dimensionGroup != null) {
                dimensionGroup.setTouched(true);
                currentSeriesDimensionGroups.add(dimensionGroup);
            }
        }
    }

    public void clearSeries() {
        currentSeriesDimensionGroups.clear();
    }

    public DimensionGroup getGroupByKey(String groupKey) {
        return dimensionGroupsByGroupKeys.get(groupKey);
    }

    public Collection<DimensionGroup> listAllGroups() {
        return dimensionGroupsByGroupKeys.values();
    }

    public void setGroupsProcessingState(GroupsProcessingState groupsProcessingState) {
        if (currentGroupInProgress != null && MapUtils.isNotEmpty(currentGroupInProgress.getDimensions())) {
            String groupKey = buildGroupKey(currentGroupInProgress);
            dimensionGroupsByGroupKeys.compute(groupKey, (key, prevValue) -> {
                if (prevValue == null) {
                    return currentGroupInProgress;
                }
                return mergeGroupKeys(prevValue, currentGroupInProgress);
            });
        }
        currentGroupInProgress = null;
        this.groupsProcessingState = groupsProcessingState;
    }

    private DimensionGroup mergeGroupKeys(DimensionGroup prevValue, DimensionGroup newValue) {
        if (!prevValue.getDimensions().equals(newValue.getDimensions())) {
            throw new IllegalArgumentException("merging DimensionGroup with different dimensions is not allowed.");
        }
        Map<String, String> dimensions = prevValue.getDimensions();


        Map<String, List<String>> attributes = new HashMap<>(prevValue.getAttributes());
        attributes.putAll(newValue.getAttributes()); //TODO: detect conflicts

        boolean isTouched = prevValue.isTouched() || newValue.isTouched();

        return new DimensionGroup(dimensions, attributes, isTouched);
    }

    private String buildGroupKey(DimensionGroup dimensionGroup) {
        GroupKeyCreator groupKeyCreator = null;
        var dimGroupDimensions = dimensionGroup.getDimensions().keySet();
        for (var existingGroupKeyCreator : groupKeyCreators) {
            if (existingGroupKeyCreator.matches(dimGroupDimensions)) {
                groupKeyCreator = existingGroupKeyCreator;
                break;
            }
        }
        if (groupKeyCreator == null) {
            groupKeyCreator = new GroupKeyCreator(dimGroupDimensions);
            groupKeyCreators.add(groupKeyCreator);
        }
        return groupKeyCreator.create(dimensionGroup.getDimensions());
    }

    public Map<String, DimensionGroup> getDimensionGroupsByGroupKeys() {
        return dimensionGroupsByGroupKeys;
    }

    public List<GroupKeyCreator> getGroupKeyCreators() {
        return groupKeyCreators;
    }

    public List<DimensionGroup> getCurrentSeriesDimensionGroups() {
        return currentSeriesDimensionGroups;
    }

    public GroupsProcessingState getGroupsProcessingState() {
        return groupsProcessingState;
    }

    public DimensionGroup getCurrentGroupInProgress() {
        return currentGroupInProgress;
    }

    public void setCurrentGroupInProgress(DimensionGroup currentGroupInProgress) {
        this.currentGroupInProgress = currentGroupInProgress;
    }
}
