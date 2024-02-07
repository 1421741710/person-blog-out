package com.itluo.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.dto.CommentInDTO;
import com.itluo.dto.CommentPageQueryDTO;
import com.itluo.entity.Article;
import com.itluo.entity.Comment;
import com.itluo.entity.User;
import com.itluo.exception.ArticleIDNotFoundException;
import com.itluo.exception.DeletionNotAllowedException;
import com.itluo.mapper.ArticleMapper;
import com.itluo.mapper.CommentMapper;
import com.itluo.mapper.UserInfoMapper;
import com.itluo.mapper.UserMapper;
import com.itluo.result.PageResult;
import com.itluo.service.CommentService;
import com.itluo.vo.CommentListVO;
import com.itluo.vo.CommentVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private UserMapper userMapper;


    /**
     * 分页查询
     * @param commentPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(CommentPageQueryDTO commentPageQueryDTO) {
        PageHelper.startPage(commentPageQueryDTO.getPage(),commentPageQueryDTO.getPageSize());
        Page<CommentVO> page = commentMapper.pageQuery(commentPageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 删除评论
     * @param id
     */
    @Override
    @Transactional(rollbackFor = {DeletionNotAllowedException.class})
    public void delete(List<Long> id) {
        List<Long> comments = commentMapper.batchSelectByIds(id);
        commentMapper.batchDeleteByIds(comments);
    }

    /**
     * 获取评论
     * @param id
     * @return
     */
    @Override
    public Map<String,Object> findByComment(Long id) {
        Article byArticleId = articleMapper.findByArticleId(id);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
        Map<String,Object> map = new HashMap<>(2);
        Long number = commentMapper.findByCommentArticleIdNum(id);
        List<CommentListVO> commentList = commentMapper.findByCommentArticleId(id);
        map.put("number",number);
        map.put("data",commentList);
        return map;
    }

    /**
     * 添加评论
     * @param commentInDTO
     */
    @Override
    public void insert(CommentInDTO commentInDTO) {
        User byUserId = userMapper.findByUserId(commentInDTO.getUserId());
        if (byUserId == null){
            throw new ArticleIDNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        Article byArticleId = articleMapper.findByArticleId(commentInDTO.getArticleId());
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentInDTO,comment);
        commentMapper.insert(comment);
    }
}
