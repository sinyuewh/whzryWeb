package com.jsj.common.service;/**
 * Created by jinshouji on 2018/4/26.
 */

/**
 * @author ：jinshouji
 * @create ：2018-04-26 9:33
 **/

import com.jsj.common.repository.BaseRepository;
import com.jsj.common.utils.jpa.DynamicSpecifications;
import com.jsj.common.utils.jpa.PageUtil;
import com.jsj.common.utils.jpa.SearchField;
import com.jsj.common.utils.jpa.SearchFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.SQLQuery;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <通用Service类>
 *
 * @author 陆小凤
 * @version [版本号1.0, 2015年11月2日]
 */
@Slf4j
public abstract class BaseService<T, ID extends Serializable> {

    public static final String PERCENT = "%";

    @Autowired
    protected BaseRepository<T, ID> baseRepository;

    private Class<T> entityClass;

    @PersistenceContext
    private EntityManager entityManager;

    @PersistenceContext
    private EntityManager em;

    //对应实体中表的Name
    private String tableName;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @SuppressWarnings("unchecked")
    public BaseService()
    {
            if (null == entityClass)
            {
                Type type = getClass().getGenericSuperclass();
                if (!(type instanceof ParameterizedType))
                {
                    type = getClass().getSuperclass().getGenericSuperclass();
                }
                entityClass = (Class<T>) ((ParameterizedType) type).getActualTypeArguments()[0];

                //通过注解去得到表的Name
                Annotation[] allAnnos = entityClass.getAnnotations();
                for(Annotation a1 : allAnnos)
                {
                    if(a1 instanceof javax.persistence.Table)
                    {
                        javax.persistence.Table table=(javax.persistence.Table)a1;
                        setTableName(table.name());
                        break;
                    }
                }
                //没有定义Table的注解或name为空，则直接去类名字的最后一位
                if(StringUtils.isEmpty(this.getTableName()))
                {
                    log.error(entityClass.getName());
                    String[] arr1=entityClass.getName().split("\\.");
                    this.tableName=arr1[arr1.length-1];
                }
            }
    }

    @Transactional(readOnly = false)
    public T save(T entity) {
        return baseRepository.save(entity);
    }

    @Transactional(readOnly = false)
    public void delete(ID id) {
        baseRepository.delete(id);
    }

    @Transactional(readOnly = false)
    public void delete(T entity) {
        baseRepository.delete(entity);
    }

    @Transactional(readOnly = false)
    public void delete(List<T> entities) {
        baseRepository.delete(entities);
    }

    @Transactional(readOnly = false)
    public void deleteAll() {
        baseRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public T findOne(ID id) {
        return baseRepository.findOne(id);
    }

    @Transactional(readOnly = true)
    public T findOneNotDeleted(ID id) {
        if (id == null || StringUtils.isBlank((String) id)) {
            return null;
        }
        Map<String, Object> searchParams = new HashMap<String, Object>();
        searchParams.put("EQ_id", id);
        searchParams.put("EQ_deleteState", false);
        return this.findOne(searchParams);
    }

    @Transactional(readOnly = true)
    public T findOne(Map<String, Object> searchParams) {
        Specification<T> spec = PageUtil.buildSpecification(searchParams, entityClass);
        return baseRepository.findOne(spec);
    }

    @Transactional(readOnly = true)
    public boolean exists(ID id) {
        return baseRepository.exists(id);
    }

    @Transactional(readOnly = true)
    public Long count(Map<String, Object> searchParams) {
        Specification<T> spec = PageUtil.buildSpecification(searchParams, entityClass);
        return baseRepository.count(spec);
    }

    @Transactional(readOnly = true)
    public List<T> findAll() {
        return (List<T>) baseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<T> findAll(List<ID> ids) {
        return (List<T>) baseRepository.findAll(ids);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize);
        return findAll(searchParams, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize, Sort.Direction direction,
                           String orderBy) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBy);
        return findAll(searchParams, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize, Sort.Direction direction,
                           String... orderBys) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBys);
        return findAll(searchParams, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize, Sort.Direction direction,
                           List<String> orderBys) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, direction, orderBys);
        return findAll(searchParams, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, int pageNumber, int pageSize, List<Sort.Direction> directions,
                           List<String> orderBys) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageNumber, pageSize, directions, orderBys);
        return findAll(searchParams, pageRequest);
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Map<String, Object> searchParams, PageRequest pageRequest) {
        Specification<T> spec = PageUtil.buildSpecification(searchParams, entityClass);
        return baseRepository.findAll(spec, pageRequest);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Map<String, Object> searchParams) {
        Specification<T> spec = PageUtil.buildSpecification(searchParams, entityClass);
        return baseRepository.findAll(spec);
    }

    @Transactional(readOnly = true)
    public List<T> findAll(Map<String, Object> searchParams, Sort sort) {
        Specification<T> spec = PageUtil.buildSpecification(searchParams, entityClass);
        return baseRepository.findAll(spec, sort);
    }

    /**
     * 批量插入数据
     *
     * @param entityList
     * @return
     */
    @Transactional(readOnly = false)
    public int batchInsert(List<T> entityList) {
        if (!CollectionUtils.isEmpty(entityList)) {
            for (int i = 0; i < entityList.size(); i++) {
                em.persist(entityList.get(i));
                if (i % 20 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            return entityList.size();
        }
        return 0;
    }

    /**
     * 批量更新
     *
     * @param entityList
     * @return
     */
    @Transactional(readOnly = false)
    public int batchUpdate(List<T> entityList) {
        if (!CollectionUtils.isEmpty(entityList)) {
            for (int i = 0; i < entityList.size(); i++) {
                em.merge(entityList.get(i));
                if (i % 20 == 0) {
                    em.flush();
                    em.clear();
                }
            }
            return entityList.size();
        }
        return 0;
    }

    /**
     * 创建动态查询条件组合.
     */
    public Specification<T> buildSpecification(Map<String, Object> searchParams) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = DynamicSpecifications.bySearchFilter(filters.values(), entityClass);
        return spec;
    }

    @Transactional(readOnly = true)
    public T queryJPQLOne(String jpql, Map<String, Object> parameters) {
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        buildQueryWithParameters(query, parameters);
        query.setFirstResult(0);
        query.setMaxResults(1);

        List<T> resultList = query.getResultList();
        if (resultList.size() != 0) {
            return resultList.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<T> queryJPQLAll(String jpql, Map<String, Object> parameters) {
        TypedQuery<T> query = em.createQuery(jpql, entityClass);
        buildQueryWithParameters(query, parameters);
        List<T> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 功能说明：根据实体分页查询
     *
     * @param jpqlSelect jpql语句的select部分, 示例：select xx, xx, xx, ... ，不包含from部分
     * @param jpqlFrom   jpql语句的from部分，示例：from entityName where ...
     * @param paremeters 参数信息
     */
    @Transactional(readOnly = true)
    public <T> Page<T> getJPQLPage(String jpqlSelect, String jpqlFrom, int pageIndex, int pageSize, Map<String, Object> paremeters) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        PageImpl page = new PageImpl(new ArrayList(), pageRequest, 0l);
        if (pageIndex <= 0 || pageSize <= 0) {
            return page;
        }
        String countJPQL = "select count(1) " + jpqlFrom;
        Query query = em.createQuery(countJPQL);
        buildQueryWithParameters(query, paremeters);

        Object valueObject = query.getSingleResult();
        BigInteger count = new BigInteger("0");
        if (valueObject != null) {
            // 查询记录条数
            count = (BigInteger) valueObject;
        }

        if (count.longValue() <= 0) {
            return page;
        }
        String queryJPQL = jpqlSelect + " " + jpqlFrom;
        query = em.createQuery(queryJPQL, entityClass);
        buildQueryWithParameters(query, paremeters);
        // 查询详细数据
        int startRow = (pageIndex - 1) * pageSize;
        query.setFirstResult(startRow);
        query.setMaxResults(pageSize);
        List<T> list = query.getResultList();
        list = (list == null ? new ArrayList<T>() : list);
        page = new PageImpl(list, pageRequest, count.longValue());
        return page;
    }

    @Transactional(readOnly = true)
    public <E> List<E> getJPQLPageList(int offset, int pageNumber, String sql, Map<String, Object> paremeters) {
        Query query = em.createQuery(sql, entityClass);
        buildQueryWithParameters(query, paremeters);
        // 查询详细数据
        query.setFirstResult(offset);
        query.setMaxResults(pageNumber);
        List list = query.getResultList();
        return list;
    }

    @Transactional(readOnly = true)
    public T querySQLOne(String sql, Map<String, Object> parameters) {
        Query query = em.createQuery(sql, entityClass);
        buildQueryWithParameters(query, parameters);
        query.setFirstResult(0);
        query.setMaxResults(1);

        List<T> resultList = query.getResultList();
        if (resultList.size() != 0) {
            return resultList.get(0);
        }
        return null;
    }

    @Transactional(readOnly = true)
    public List<T> querySQLAll(String sql, Map<String, Object> parameters) {
        TypedQuery<T> query = (TypedQuery<T>) em.createNativeQuery(sql, entityClass);
        buildQueryWithParameters(query, parameters);
        List<T> resultList = query.getResultList();
        return resultList;
    }

    /**
     * 功能说明：根据实体分页查询
     *
     * @param sqlSelect  sql语句的select部分, 示例：select xx, xx, xx, ... ，不包含from部分
     * @param sqlFrom    sql语句的from部分，示例：from table_name where ...
     * @param paremeters 参数信息
     */
    @Transactional(readOnly = true)
    public <T> Page<T> getSQLPage(String sqlSelect, String sqlFrom, int pageIndex, int pageSize, Map<String, Object> paremeters) {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        PageImpl page = new PageImpl(new ArrayList(), pageRequest, 0l);
        if (pageIndex <= 0 || pageSize <= 0) {
            return page;
        }
        String countSql = "select count(1) " + sqlFrom;
        Query query = em.createNativeQuery(countSql);
        buildQueryWithParameters(query, paremeters);

        Object valueObject = query.getSingleResult();
        BigInteger count = new BigInteger("0");
        if (valueObject != null) {
            // 查询记录条数
            count = (BigInteger) valueObject;
        }

        if (count.longValue() <= 0) {
            return page;
        }
        String querySql = sqlSelect + " " + sqlFrom;
        query = em.createNativeQuery(querySql, entityClass);
        buildQueryWithParameters(query, paremeters);
        // 查询详细数据
        int startRow = (pageIndex - 1) * pageSize;
        query.setFirstResult(startRow);
        query.setMaxResults(pageSize);
        List<T> list = query.getResultList();
        list = (list == null ? new ArrayList<T>() : list);
        page = new PageImpl(list, pageRequest, count.longValue());
        return page;
    }

    @Transactional(readOnly = true)
    public <E> List<E> getSQLPageList(int offset, int pageNumber, String sql, Map<String, Object> paremeters) {
        Query query = em.createNativeQuery(sql, entityClass);
        buildQueryWithParameters(query, paremeters);
        // 查询详细数据
        query.setFirstResult(offset);
        query.setMaxResults(pageNumber);
        List list = query.getResultList();
        return list;
    }

    public Map<String, Object> createParameters(String parameter, Object value) {
        Map<String, Object> parameterMap = new HashMap<String, Object>();
        parameterMap.put(parameter, value);
        return parameterMap;

    }

    @Transactional(readOnly = false)
    public int executeNativeSQL(String sql) {
        if (StringUtils.isNotBlank(sql)) {
            return em.createNativeQuery(sql).executeUpdate();
        }
        return 0;
    }

    @Transactional(readOnly = false)
    public int executeNativeSQL(String sql, Map<String, Object> parameters) {
        if (StringUtils.isNotBlank(sql)) {
            Query query = em.createNativeQuery(sql);
            buildQueryWithParameters(query, parameters);
            return query.executeUpdate();
        }
        return 0;
    }

    /**
     * 根据参数map，填充query的参数内容
     *
     * @param query
     * @param parameters
     */
    public void buildQueryWithParameters(Query query, Map<String, Object> parameters) {
        if (MapUtils.isNotEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object obj = entry.getValue();
                if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
    }


    public String leftLike(String likeValue) {
        return PERCENT + likeValue;
    }

    public String rightLike(String likeValue) {
        return likeValue + PERCENT;
    }

    public String fullLike(String likeValue) {
        return PERCENT + likeValue + PERCENT;
    }


    public long getResultCount(SqlBuilderBase sqlBase) {
        String countSQL = sqlBase.getCountSQL();
        Query nativeQuery = entityManager.createNativeQuery(countSQL);
        sqlBase.putParameter(nativeQuery);
        BigInteger count = (BigInteger) nativeQuery.getSingleResult();
        return count.longValue();

    }

    public Page getResultRecords(SqlBuilderBase sqlBase, long count) {

        javax.persistence.Query queryList = entityManager.createNativeQuery(sqlBase.getSelectFormSQL());
        queryList.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        sqlBase.putParameter(queryList);

        Page page = PageUtil.getResultPage(queryList, count, sqlBase.pageIndex, sqlBase.pageSize);
        return page;
    }

    public List getResultRecords(SqlBuilderBase sqlBase) {
        javax.persistence.Query queryList = entityManager.createNativeQuery(sqlBase.getSelectFormSQL());
        queryList.unwrap(SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        sqlBase.putParameter(queryList);
        List resultList = queryList.getResultList();
        return resultList;
    }

    public Page<Map<String, Object>> getPageRecords(SqlBuilderBase sqlBase) {

        long count = getResultCount(sqlBase);
        Page resultRecords = getResultRecords(sqlBase, count);

        return resultRecords;
    }

    public List getListRecords(SqlBuilderBase sqlBase) {
        return getResultRecords(sqlBase);
    }

    //----------------------------------------------------------------------------------------------------------------->>
    //#region 增加的方法（金寿吉)(时间：2018年4月26日）
    /**
     * 功能说明：根据实体分页查询
     *
     * @param sqlSelect      sql语句的select部分, 示例：select xx, xx, xx, ... ，不包含from部分或使用{from}和{where}占位符
     * @param sqlFrom        sql语句的from部分，示例：from table_name where ...
     * @param condition      条件参数信息
     * @param isToMap        是否将结果已map的方式输出（不选择，则使用true）
     */
    @Transactional(readOnly = true)
    public Page getNativeSQLPage(String sqlSelect, String sqlFrom, int pageIndex,
                                 int pageSize, List<SearchField> condition,boolean isToMap)
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        PageImpl page = new PageImpl(new ArrayList(), pageRequest, 0l);
        if (pageIndex <= 0 || pageSize <= 0) {
            return page;
        }
        //计算数据的总行数
        String countSql = "select count(1) " + sqlFrom;

        //得到查询的条件
        String conditionSql=SearchField.getSqlWhere(condition);
        if(StringUtils.isEmpty(conditionSql)==false) {
            countSql=countSql+" where "+conditionSql;
        }
        Query query = em.createNativeQuery(countSql);
        buildQueryWithSearchFieldList(query, condition);

        Object valueObject = query.getSingleResult();
        BigInteger count = new BigInteger("0");
        if (valueObject != null) {
            // 查询记录条数
            count = (BigInteger) valueObject;
        }
        if (count.longValue() <= 0) {
            return page;
        }
        //2）返回数据的sql语句-------------------------------------------------------
        String querySql = sqlSelect;
        if(querySql.indexOf("{from}")>0)    //处理sql语句中{from}的情况
        {
            querySql=querySql.replace("{from}",sqlFrom);
        }
        else {
            querySql = querySql + " " + sqlFrom;
        }
        if(StringUtils.isEmpty(conditionSql)==false) {
            if(querySql.indexOf("{where}")>0)
            {
                querySql=querySql.replace("{where}",conditionSql);
            }
            else {
                querySql = querySql + " where " + conditionSql;
            }
        }
        else
        {
            if(querySql.indexOf("{where}")>0)
            {
                querySql=querySql.replace("{where}"," 1=1 ");  //没有条件，则用1=1填充
            }
        }
        //执行sql语句并返回
        Query query1 = em.createNativeQuery(querySql);
        buildQueryWithSearchFieldList(query1, condition);
        query1.setFirstResult((pageIndex-1) * pageSize).setMaxResults(pageSize);
        if(isToMap) {
            query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        }
        List<Map<String, Object>> data=query1.getResultList();

        data = (data == null ? new ArrayList<Map<String, Object>>() :data);
        page = new PageImpl(data, pageRequest, count.longValue());

        //返回数据
        return page;
    }

    @Transactional(readOnly = true)
    public Page getNativeSQLPage(String sqlSelect, String sqlFrom, int pageIndex, 
                                 int pageSize, List<SearchField> condition)
    {
        return getNativeSQLPage(sqlSelect,sqlFrom,pageIndex,pageSize,condition,true);
    }
    //------------------------------------------------------------------------------------------------------------------
    /**
     * 功能说明：执行原生的update/delete/insert 的sql语句（避免先读出数据，更新后再保存）
     *
     * @param updateSql         更新的的Sql语句 更新的字段 参数和where 的条件参数
     * @param parameters        参数信息 in的参数需要用List （包括更新的字段参数 和条件中的参数）
     * @param isAutoFlush       是否自动更新，不更新则会回滚，需要手动执行 entityManager.flush();
     * @Return                   返回更新(新增、更改和删除）的数据条数
     */
    @Transactional
    public int executeNativeUpateSql(String updateSql,Map<String, Object> parameters,boolean isAutoFlush)
    {
        javax.persistence.Query query = entityManager.createNativeQuery(updateSql);
        if (null!=parameters && MapUtils.isNotEmpty(parameters)) {
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                Object obj = entry.getValue();
                if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }
        }
        int result = query.executeUpdate();
        if(isAutoFlush) {
            entityManager.flush();    //一定要刷新才能
        }
        return result;
    }

    public int executeNativeUpateSql(String updateSql,Map<String, Object> parameters) {
        return executeNativeUpateSql(updateSql,parameters,true);
    }

    public int executeNativeUpateSql(String updateSql) {
        return executeNativeUpateSql(updateSql,null,true);
    }
    //------------------------------------------------------------------------------------------------------------------
    // 针对单表的操作
    /**
     * 功能说明：            利用Map生成Insert的sql语句插入数据
     * @param parameters   插入的数据Map
     * @Return              返回插入的数据条数
     */
     public int insertData(Map<String, Object> parameters)
    {
        String sql1="insert into {tablename} ({fields}) values ({values})";
        StringBuilder sb1=new StringBuilder();
        StringBuilder sb2=new StringBuilder();

        if (null!=parameters && MapUtils.isNotEmpty(parameters) && parameters.size()>0 )
        {
            //构建插入的sql语句
            boolean first=true;
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                if(first==false)
                {
                    sb1.append(",");
                    sb2.append(",");
                }
                else
                {
                    first=false;
                }
                sb1.append(entry.getKey());
                sb2.append(":").append(entry.getKey());
            }

            //形成完整的sql语句
            sql1= sql1.replace("{tablename}",this.getTableName()).replace("{fields}",sb1.toString())
                    .replace("{values}",sb2.toString());
            //执行sql语句
            return this.executeNativeUpateSql(sb1.toString(),parameters);
        }
        else
        {
            return 0;
        }
    }

    /**
     * 功能说明：根据条件生成sql语句执行删除（物理删除）操作
     * @param condition  删除数据的条件，如果是like，要在值的合适位置添加%和_等符合
     * @Return 返回删除的数据条数
     */
    public int deleteData(List<SearchField> condition)
    {
        String sql1="delete from {tablename} where {condition}";
        if(null==condition || condition.size()==0) {
            return 0;
        }
        else
        {
            String sqlWhere=SearchField.getSqlWhere(condition);
            sql1=sql1.replace("{tablename}",this.getTableName()).replace("{condition}",sqlWhere);
            Map<String, Object> parameters=new HashMap<String,Object>();
            for(SearchField s1 : condition)
            {
                parameters.put(s1.getFieldName(),s1.getFieldValue());
            }
            return this.executeNativeUpateSql(sql1,parameters);
        }
    }
    
    /**
     * 功能说明：根据条件和Map生成update的sql语句执行更新操作
     * @param condition  更新数据的条件，如果是like，要在值的合适位置添加%和_等符合
     * @param parameters  更新的字段和值的Map
     * @Return 返回更新的数据条数
     */
    public int updateData(Map<String, Object> parameters, List<SearchField> condition)
    {
        String sql1=" update {tablename} set {updatelist} where {condition} ";
        sql1= sql1.replace("{tablename}",this.getTableName());

        StringBuilder sb1=new StringBuilder();
        if (null!=parameters && MapUtils.isNotEmpty(parameters) && parameters.size()>0 ) {
            //构建插入的sql语句
            boolean first = true;
            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                if (first == false) {
                    sb1.append(",");
                } else {
                    first = false;
                }
                sb1.append(entry.getKey()).append("= :").append(entry.getKey());
            }
            sql1=sql1.replace("{updatelist}",sb1.toString());
            String where1=SearchField.getSqlWhere(condition);
            if(StringUtils.isEmpty(where1))
            {
                 where1="0=1";  //没有条件，则不执行更新
            }
            else
            {
                //把条件中的参数加入到参数列表
                for(SearchField s1 : condition)
                {
                    if(StringUtils.isEmpty(s1.getFieldName())==false && s1.getFieldValue()!=null
                            && StringUtils.isEmpty(s1.getFieldValue().toString())==false) {
                        parameters.put(s1.getFieldName(), s1.getFieldValue());
                    }
                }
            }
            sql1=sql1.replace("{condition}",where1);
            return this.executeNativeUpateSql(sql1,parameters);
        }
        return 0;
    }

    //------------------------------------------------------------------------------------------------------------------
    /**
     * 功能说明：找到满足条件的Page数据
     * @param condition  数据的条件，如果是like，要在值的合适位置添加%和_等符合
     * @param fields     返回的字段列表，支持* 和列别名、计算列等
     * @Return 返回的 满足条件的Page数据，如pageIndex 或 pageSize<0，则返回所有的数据
     */
    @Transactional(readOnly = true)
    public Page<List<Map<String, Object>>> getPageListMapData(String fields,String orderBy,
            List<SearchField> condition,int pageIndex,int pageSize )
    {
        return this.getPageListMapData(fields,orderBy,"",condition,pageIndex,pageSize);
    }

    @Transactional(readOnly = true)
    public Page<List<Map<String, Object>>> getPageListMapData(String fields,String orderBy,String groupBy,
                                                              List<SearchField> condition,int pageIndex,int pageSize )
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        PageImpl page = new PageImpl(new ArrayList(), pageRequest, 0l);

        //计算数据的总行数
        String countSql = "select count(1) from " + this.getTableName();

        //得到查询的条件
        String conditionSql=SearchField.getSqlWhere(condition);
        if(StringUtils.isEmpty(conditionSql)==false) {
            countSql=countSql+" where "+conditionSql;
        }
        Query query = em.createNativeQuery(countSql);
        buildQueryWithSearchFieldList(query, condition);

        Object valueObject = query.getSingleResult();
        BigInteger count = new BigInteger("0");
        if (valueObject != null) {
            // 查询记录条数
            count = (BigInteger) valueObject;
        }
        if (count.longValue() <= 0) {
            return page;
        }
        //2）返回数据的sql语句-------------------------------------------------------
        String querySql = "select "+fields+" from "+this.getTableName();
        if(StringUtils.isEmpty(conditionSql)==false) {
            querySql = querySql + " where " + conditionSql;
        }

        if(StringUtils.isEmpty(groupBy)==false) {
            querySql = querySql + " group by " + groupBy;
        }

        if(StringUtils.isEmpty(orderBy)==false) {
            querySql = querySql + " order by " + orderBy;
        }

        //执行sql语句并返回
        Query query1 = em.createNativeQuery(querySql);
        buildQueryWithSearchFieldList(query1, condition);

        //pageIndex 或pageSize <0 则返回所有的数据
        if(pageIndex>0 && pageSize>0) {
            query1.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        }
        query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        List<Map<String, Object>> data=query1.getResultList();

        data = (data == null ? new ArrayList<Map<String, Object>>() :data);
        page = new PageImpl(data, pageRequest, count.longValue());

        //返回数据
        return page;
    }

    @Transactional(readOnly = true)
    public Page<List<Map<String, Object>>> getPageListMapData(String sql,int pageIndex,int pageSize )
    {
        PageRequest pageRequest = PageUtil.buildPageRequest(pageIndex, pageSize);
        PageImpl page = new PageImpl(new ArrayList(), pageRequest, 0l);

        //执行sql语句并返回
        Query query1 = em.createNativeQuery(sql);
        buildQueryWithSearchFieldList(query1, null);

        //pageIndex 或pageSize <0 则返回所有的数据
        if(pageIndex>0 && pageSize>0) {
            query1.setFirstResult((pageIndex - 1) * pageSize).setMaxResults(pageSize);
        }
        query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        List<Map<String, Object>> data=query1.getResultList();

        data = (data == null ? new ArrayList<Map<String, Object>>() :data);
        page = new PageImpl(data, pageRequest, data.size());

        //返回数据
        return page;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getListData(String sql)
    {
        //执行sql语句并返回
        Query query1 = em.createNativeQuery(sql);
        buildQueryWithSearchFieldList(query1, null);

        query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        List<Map<String, Object>> data=query1.getResultList();

        data = (data == null ? new ArrayList<Map<String, Object>>() :data);

        //返回数据
        return data;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> getFirstData(String sql)
    {
        //执行sql语句并返回
        Query query1 = em.createNativeQuery(sql);
        buildQueryWithSearchFieldList(query1, null);

        query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        List<Map<String, Object>> data=query1.getResultList();

        data = (data == null ? new ArrayList<Map<String, Object>>() :data);

        //返回数据
        if(data!=null && data.size()>0)
        {
            return data.get(0);
        }
        return null;
    }

    /**
     * 功能说明：找到满足条件的第1条数据
     * @param condition   数据的条件，如果是like，要在值的合适位置添加%和_等符合
     * @param fields      返回的字段列表，支持* 和列别名、计算列等
     * @Return             返回的第一条数据Map
     */
    @Transactional(readOnly = true)
    public Map<String, Object> getFirstDataToMap(String fields,String orderBy,List<SearchField> condition)
    {
        String querySql = "select "+fields+" from "+this.getTableName();
        String conditionSql=SearchField.getSqlWhere(condition);

        if(StringUtils.isEmpty(conditionSql)==false) {
            querySql = querySql + " where " + conditionSql;
        }

        if(StringUtils.isEmpty(orderBy)==false) {
            querySql = querySql + " order by " + orderBy;
        }

        //执行sql语句并返回
        Query query1 = em.createNativeQuery(querySql);
        buildQueryWithSearchFieldList(query1, condition);

        //pageIndex 或pageSize <0 则返回所有的数据
        query1.setFirstResult(0).setMaxResults(1);
        query1.unwrap(org.hibernate.SQLQuery.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  //将结果设置为Map
        List<Map<String, Object>> data=query1.getResultList();

        if(null!=data && data.size()>0)
        {
            return data.get(0);
        }
        else
        {
            return null;
        }
    }
    //endregion
    
    //#region 私有方法
    /**
     * 根据查询条件构建查询参数
     * @Author 金寿吉
     * @param query
     * @param parameters
     */
    private void buildQueryWithSearchFieldList(Query query, List<SearchField> parameters) {
        if(parameters!=null && parameters.size()>0) {
            for (SearchField entry : parameters) {
                Object obj = entry.getFieldValue();
                if (obj != null && StringUtils.isNotEmpty(obj.toString())) {
                    query.setParameter(entry.getFieldName(), entry.getFieldValue());
                }
            }
        }
    }
    //endregion
}

