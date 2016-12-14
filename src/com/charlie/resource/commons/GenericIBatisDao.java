package com.charlie.resource.commons;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

import com.charlie.resource.util.DateUtil;

public class GenericIBatisDao<T, PK extends Serializable> extends AutowiredSqlMapClientDaoSupport {

    public static final String SQLID_INSERT = "insert";
    public static final String SQLID_UPDATE = "update";
    public static final String SQLID_DELETE = "delete";
    public static final String SQLID_FINDALL = "findAll";
    public static final String SQLID_FINDBYID = "findById";
    public static final String SQLID_FINDBYQUERYCONDITION = "findByQueryCondition";
    public static final String SQLID_FINDTOTALCOUNT = "findTotalCount";
    protected String namespace;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @SuppressWarnings("unchecked")
    public T findById(PK sysid) {
        return (T) getSqlMapClientTemplate().queryForObject(
                namespace + "." + SQLID_FINDBYID, sysid);
    }

    public List<T> findAll() throws Exception {
        return findByQueryCondition(QueryConditionModel.createQueryAllConditionModel());
    }

    @SuppressWarnings("unchecked")
    public List<T> findByQueryCondition(QueryConditionModel<?> qcModel) throws Exception {
        if (qcModel == null) {
            throw new Exception("error.qcModel.null");
        }

        if (qcModel.getPageno() == QueryConditionModel.PAGENO_QUERY_ALL) { // findAll
            //qcModel.setConditions(null);
            return getSqlMapClientTemplate().queryForList(namespace + "." + SQLID_FINDBYQUERYCONDITION, qcModel);
        }
        
        return getSqlMapClientTemplate().queryForList(namespace + "." + SQLID_FINDBYQUERYCONDITION, qcModel, qcModel.getStartidx(), qcModel.getPagesize());

    }

    public int findTotalCount(QueryConditionModel<?> qcModel) {
        return (Integer) getSqlMapClientTemplate().queryForObject(
                namespace + "." + SQLID_FINDTOTALCOUNT, qcModel);

    }

    public int findTotalCount(ConditionModel condition) {
        QueryConditionModel<?> qcModel = QueryConditionModel.createQueryAllConditionModel();
        //qcModel.setConditionstr(conditionstr);

        qcModel.addCondition(condition);

        return findTotalCount(qcModel);
    }

    public T insert(T model, String account) throws Exception {
        if ( model != null && model instanceof BaseModel ) {
            BaseModel baseModel = (BaseModel) model;
            baseModel.setSyscreatetime( DateUtil.nowTimestamp() );
            baseModel.setSysmodifytime( DateUtil.nowTimestamp() );
            baseModel.setSyscreator(account);
            baseModel.setSysmodifier(account);
        }
        Long sysid = (Long) getSqlMapClientTemplate().insert(namespace + "." + SQLID_INSERT, model);
        if (sysid != null) {
            try {
                BeanUtils.setProperty(model, "sysid", sysid);
            } catch (Exception e) {
                throw new Exception("error.user.insert");
            }
        } else {
            throw new Exception("error.user.insert");
        }
        return model;
    }

    public int update(T model, String account) throws Exception {
        if ( model != null && model instanceof BaseModel ) {
            BaseModel baseModel = (BaseModel) model;
            baseModel.setSysmodifytime(DateUtil.nowTimestamp());
            baseModel.setSysmodifier(account);
        }
        return getSqlMapClientTemplate().update(namespace + "." + SQLID_UPDATE, model);
    }

    public int delete(PK sysid, String account) throws Exception {
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("sysid", sysid);
		map.put("sysmodifier", account);
        return getSqlMapClientTemplate().delete(namespace + "." + SQLID_DELETE, map);
    }
}
