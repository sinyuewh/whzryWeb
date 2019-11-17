package com.jsj.webapi.repository.sysframe;

import com.jsj.common.repository.BaseRepository;
import com.jsj.webapi.dataobject.sysframe.Attachment;

import java.util.List;

/**
 * Created by jinshouji on 2018/10/21.
 */
public interface AttachmentRepository extends BaseRepository<Attachment, Integer> {

    //根据父数据的id，返回多条附件的数据
    List<Attachment> findByParentId(String parentId);
}
