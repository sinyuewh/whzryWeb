package com.jsj.common.utils.jpa;/**
 * Created by jinshouji on 2018/4/26.
 */

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Map;

/**
 * @author ：jinshouji
 * @create ：2018-04-26 9:57
 * @remark ：分页工具类
 **/
public class PageUtil
{
    public static Page getResultPage(javax.persistence.Query query, long count, int pageNumber, int pageSize){
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize);
        query.setFirstResult(( pageRequest.getPageNumber() ) *    pageRequest.getPageSize()).setMaxResults(  pageRequest.getPageSize());
        List resultList = query.getResultList();
        PageImpl page = new PageImpl(resultList, pageRequest,count);
        return page;
    }
    public static PageRequest buildPageRequest(int pageNumber, int pageSize) {
        Sort sort = null;
        return buildPageRequest(pageNumber, pageSize, sort);
    }

    /**
     * <创建分页请求,并按照指定的顺序和属性进行排序 单个字段排序><br />
     * <比如direction为"desc",oderBy为id,会按照id降序>
     *
     * @param pageNumber
     * @param pageSize
     * @param direction
     * @param orderBy
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Sort.Direction direction, String orderBy) {
        Sort sort = null;
        if (null != direction && StringUtils.isNotBlank(orderBy)) {
            sort = new Sort(direction, orderBy);
        }
        return buildPageRequest(pageNumber, pageSize, sort);
    }

    public static PageRequest buildPageRequest(int pageNumber, int pageSize, String direction, String... orderBys) {
        Sort sort = null;
        if ("desc".equalsIgnoreCase(direction)) {
            sort = new Sort(Sort.Direction.DESC, orderBys);
        } else {
            sort = new Sort(Sort.Direction.ASC, orderBys);
        }
        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * <创建分页请求,并按照指定的顺序和属性进行排序 排序方向相同的多个字段排序><br />
     * <比如direction为"desc",oderBy为{"id","age"},会按照id降序,然后age降序>
     *
     * @param pageNumber
     * @param pageSize
     * @param direction
     * @param orderBys
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Sort.Direction direction, String... orderBys) {
        Sort sort = null;
        if (null != direction && ArrayUtils.isNotEmpty(orderBys)) {
            sort = new Sort(direction, orderBys);
        }
        return buildPageRequest(pageNumber, pageSize, sort);
    }

    /**
     * <创建分页请求,并按照指定的顺序和属性进行排序 排序方向相同的多个字段排序><br />
     * <比如direction为"desc",oderBy为{"id","age"},会按照id降序,然后age降序>
     *
     * @param pageNumber
     * @param pageSize
     * @param direction
     * @param orderBys
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Sort.Direction direction, List<String> orderBys) {
        Sort sort = null;
        if (null != direction && CollectionUtils.isNotEmpty(orderBys)) {
            sort = new Sort(direction, orderBys);
        }
        return buildPageRequest(pageNumber, pageSize, sort);
    }

    /**
     * <创建分页请求,并按照指定的顺序和属性进行排序 排序方向不同的多个字段排序><br />
     * <例如directions中包含排序方向{"asc","desc","asc"},orderBys中包含需要排序的字段
     * {"id","age","name"},则排序的效果为先按照id升序,再按age降序,再按name升序排列.directions和oderBys list大小要相同>
     *
     * @param pageNumber
     * @param pageSize
     * @param directions
     * @param orderBys
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static PageRequest buildPageRequest(int pageNumber, int pageSize, List<Sort.Direction> directions,
                                               List<String> orderBys) {
        Sort sort = null;
        if (CollectionUtils.isNotEmpty(directions) && CollectionUtils.isNotEmpty(orderBys)) {
            Validate.isTrue((directions.size() == orderBys.size()), "directions of size eq to the size of orderBys.");
            for (int i = 0, len = directions.size(); i < len; i++) {
                if (i == 0) {
                    sort = new Sort(directions.get(i), orderBys.get(i));
                } else {
                    sort = sort.and(new Sort(directions.get(i), orderBys.get(i)));
                }
            }
        }
        return buildPageRequest(pageNumber, pageSize, sort);
    }

    public static PageRequest buildPageRequest(int pageNumber, int pageSize, Sort sort) {
        // Validate.isTrue((pageNumber >= 1), "pageNumber must be gte one.");
        // Validate.isTrue((pageSize >= 1), "pageSize must be gte one.");
        pageNumber = pageNumber < 1 ? 1 : pageNumber;
        pageSize = pageSize < 1 ? 1 : pageSize;

        return new PageRequest(pageNumber - 1, pageSize, sort);
    }

    /**
     * <创建动态查询条件组合><br />
     * <功能详细描述>
     *
     * @param searchParams
     * @param t
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static <T> Specification<T> buildSpecification(Map<String, Object> searchParams, Class<T> t) {
        Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
        Specification<T> spec = (Specification<T>) DynamicSpecifications.bySearchFilter(filters.values(), t.getClass());
        return spec;
    }
}
