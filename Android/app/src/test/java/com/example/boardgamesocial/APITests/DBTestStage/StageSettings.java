package com.example.boardgamesocial.APITests.DBTestStage;

import static com.example.boardgamesocial.API.RetrofitClient.getObjectList;

import com.example.boardgamesocial.API.RetrofitClient;
import com.example.boardgamesocial.DataClasses.DataClass;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class StageSettings<DC extends DataClass> {
    private Map<TestStage, StageAction> stageActionMap;
    private Map<String, String> stageFilterBeforeAdd;
    private Map<String, String> stageFilterAfterAdd;
    private Map<String, String> stageFilterAfterUpdate;
    private Map<String, String> stageFilterAfterDelete;
    private DataClass stageAddObject;
    private Map<String, String> stageUpdateObject;
    private Map<String, String> stageDeleteObject;
    private Class<DC> dataClass;
    private RetrofitClient retrofit;

    public StageSettings(
            Map<TestStage, StageAction> stageActionMap,
            Map<String, String> stageFilterBeforeAdd,
            Map<String, String> stageFilterAfterAdd,
            Map<String, String> stageFilterAfterUpdate,
            Map<String, String> stageFilterAfterDelete,
            DataClass stageAddObject,
            Map<String, String> stageUpdateObject,
            Class<DC> dataClass,
            RetrofitClient retrofit
    ) {
        this.stageActionMap = stageActionMap;
        this.stageFilterBeforeAdd = stageFilterBeforeAdd;
        this.stageFilterAfterAdd = stageFilterAfterAdd;
        this.stageFilterAfterUpdate = stageFilterAfterUpdate;
        this.stageFilterAfterDelete = stageFilterAfterDelete;
        this.stageAddObject = stageAddObject;
        this.stageUpdateObject = stageUpdateObject == null ? new HashMap<>() : stageUpdateObject;
        this.stageDeleteObject = new HashMap<>();
        this.dataClass = dataClass;
        this.retrofit = retrofit;
    }

    public Map<TestStage, StageAction> getStageActionMap() {
        return stageActionMap;
    }

    public void setStageActionMap(Map<TestStage, StageAction> stageActionMap) {
        this.stageActionMap = stageActionMap;
    }

    public Map<String, String> getStageFilterBeforeAdd() {
        return stageFilterBeforeAdd;
    }

    public void setStageFilterBeforeAdd(Map<String, String> stageFilterBeforeAdd) {
        this.stageFilterBeforeAdd = stageFilterBeforeAdd;
    }

    public Map<String, String> getStageFilterAfterAdd() {
        return stageFilterAfterAdd;
    }

    public void setStageFilterAfterAdd(Map<String, String> stageFilterAfterAdd) {
        this.stageFilterAfterAdd = stageFilterAfterAdd;
    }

    public Map<String, String> getStageFilterAfterUpdate() {
        return stageFilterAfterUpdate;
    }

    public void setStageFilterAfterUpdate(Map<String, String> stageFilterAfterUpdate) {
        this.stageFilterAfterUpdate = stageFilterAfterUpdate;
    }

    public Map<String, String> getStageFilterAfterDelete() {
        return stageFilterAfterDelete;
    }

    public void setStageFilterAfterDelete(Map<String, String> stageFilterAfterDelete) {
        this.stageFilterAfterDelete = stageFilterAfterDelete;
    }

    public DataClass getStageAddObject() {
        return stageAddObject;
    }

    public void setStageAddObject(DataClass stageAddObject) {
        this.stageAddObject = stageAddObject;
    }

    public Map<String, String> getStageUpdateObject() {
        return stageUpdateObject;
    }

    public void setStageUpdateObject(Map<String, String> stageUpdateObject) {
        this.stageUpdateObject = stageUpdateObject;
    }

    public Map<String, String> getStageDeleteObject() {
        return stageDeleteObject;
    }

    public void setStageDeleteObject(Map<String, String> stageDeleteObject) {
        this.stageDeleteObject = stageDeleteObject;
    }

    public Class<DC> getDataClass() {
        return dataClass;
    }

    public void setDataClass(Class<DC> dataClass) {
        this.dataClass = dataClass;
    }

    public RetrofitClient getRetrofit() {
        return retrofit;
    }

    public void setRetrofit(RetrofitClient retrofit) {
        this.retrofit = retrofit;
    }

    public void setObjectId() throws IOException, IllegalAccessException, NoSuchFieldException {
        if (!stageDeleteObject.containsKey("id") || !stageUpdateObject.containsKey("id")){
            List<DC> foundObjects = new ArrayList<>();
            retrofit.getCall(dataClass, stageFilterAfterAdd)
                    .subscribe(jsonArray -> foundObjects.addAll(getObjectList(jsonArray, dataClass)));
            Field idField = dataClass.getDeclaredField("id");
            idField.setAccessible(true);
            if (!stageDeleteObject.containsKey("id")) {
                stageDeleteObject.put("id", String.valueOf(idField.get(foundObjects.get(0))));
            }
            if (!stageUpdateObject.containsKey("id")){
                stageUpdateObject.put("id", String.valueOf(idField.get(foundObjects.get(0))));
            }
        }
    }

    public void runStageTests() throws IOException, IllegalAccessException, NoSuchFieldException {
        if (stageActionMap.containsKey(TestStage.GET_TEST_FILTER)){
            retrofit.getCall(dataClass, stageFilterBeforeAdd)
                    .subscribe(jsonArray -> Objects.requireNonNull(stageActionMap.get(TestStage.GET_TEST_FILTER))
                            .runActions(jsonArray));
        }

        if (stageActionMap.containsKey(TestStage.POST_TEST)){
            retrofit.postCall(dataClass, stageAddObject)
                    .subscribe(jsonObject -> Objects.requireNonNull(stageActionMap.get(TestStage.POST_TEST))
                            .runActions(jsonObject));
        }

        if (stageActionMap.containsKey(TestStage.GET_TEST_POST)){
            retrofit.getCall(dataClass, stageFilterAfterAdd)
                    .subscribe(jsonArray -> Objects.requireNonNull(stageActionMap.get(TestStage.GET_TEST_POST))
                            .runActions(jsonArray));
        }

        if (stageActionMap.containsKey(TestStage.PUT_TEST)){
            setObjectId();
            retrofit.putCall(dataClass, stageUpdateObject)
                    .subscribe(jsonObject -> Objects.requireNonNull(stageActionMap.get(TestStage.PUT_TEST))
                            .runActions(jsonObject));
        }

        if (stageActionMap.containsKey(TestStage.GET_TEST_PUT)){
            retrofit.getCall(dataClass, stageFilterAfterUpdate)
                    .subscribe(jsonArray -> Objects.requireNonNull(stageActionMap.get(TestStage.GET_TEST_PUT))
                            .runActions(jsonArray));
        }

        if (stageActionMap.containsKey(TestStage.DELETE_TEST)){
            setObjectId();
            retrofit.deleteCall(dataClass, stageDeleteObject)
                    .subscribe(jsonArray -> Objects.requireNonNull(stageActionMap.get(TestStage.DELETE_TEST))
                            .runActions(jsonArray));
        }

        if (stageActionMap.containsKey(TestStage.GET_TEST_DELETE)){
            retrofit.getCall(dataClass, stageFilterAfterDelete)
                    .subscribe(jsonArray -> Objects.requireNonNull(stageActionMap.get(TestStage.GET_TEST_DELETE))
                            .runActions(jsonArray));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StageSettings<?> that = (StageSettings<?>) o;
        return Objects.equals(getStageActionMap(), that.getStageActionMap())
                && Objects.equals(getStageFilterBeforeAdd(), that.getStageFilterBeforeAdd())
                && Objects.equals(getStageFilterAfterAdd(), that.getStageFilterAfterAdd())
                && Objects.equals(getStageFilterAfterUpdate(), that.getStageFilterAfterUpdate())
                && Objects.equals(getStageFilterAfterDelete(), that.getStageFilterAfterDelete())
                && Objects.equals(getStageAddObject(), that.getStageAddObject())
                && Objects.equals(getStageUpdateObject(), that.getStageUpdateObject())
                && Objects.equals(getStageDeleteObject(), that.getStageDeleteObject())
                && Objects.equals(getDataClass(), that.getDataClass())
                && Objects.equals(getRetrofit(), that.getRetrofit());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStageActionMap(),
                getStageFilterBeforeAdd(),
                getStageFilterAfterAdd(),
                getStageFilterAfterUpdate(),
                getStageFilterAfterDelete(),
                getStageAddObject(),
                getStageUpdateObject(),
                getStageDeleteObject(),
                getDataClass(),
                getRetrofit());
    }

    @Override
    public String toString() {
        return "StageSettings{" +
                "stageActionMap=" + stageActionMap +
                ", stageFilterBeforeAdd=" + stageFilterBeforeAdd +
                ", stageFilterAfterAdd=" + stageFilterAfterAdd +
                ", stageFilterAfterUpdate=" + stageFilterAfterUpdate +
                ", stageFilterAfterDelete=" + stageFilterAfterDelete +
                ", stageAddObject=" + stageAddObject +
                ", stageUpdateObject=" + stageUpdateObject +
                ", stageDeleteObject=" + stageDeleteObject +
                ", dataClass=" + dataClass +
                ", retrofit=" + retrofit +
                '}';
    }
}
