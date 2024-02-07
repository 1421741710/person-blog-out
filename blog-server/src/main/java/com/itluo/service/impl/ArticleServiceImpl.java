package com.itluo.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itluo.constant.MessageConstant;
import com.itluo.context.BaseContext;
import com.itluo.dto.ArticleInDTO;
import com.itluo.dto.ArticlePageQueryDTO;
import com.itluo.dto.ArticleUpDTO;
import com.itluo.entity.Article;
import com.itluo.entity.User;
import com.itluo.enumeration.MessageType;
import com.itluo.exception.AccountNotFoundException;
import com.itluo.exception.ArticleIDNotFoundException;
import com.itluo.mapper.*;
import com.itluo.result.PageResult;
import com.itluo.result.WebSocketMessage;
import com.itluo.service.ArticleService;
import com.itluo.vo.ArticleLoginVO;
import com.itluo.vo.ArticleVO;
import com.itluo.websocket.WebSocketServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 */
@Service
@Slf4j
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private ArticleUserMapper articleUserMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private FavoritesMapper favoritesMapper;

    @Autowired
    private HistoryMapper historyMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private WebSocketServer webSocketServer;


    /**
     * 分页查询
     * @param articlePageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(ArticlePageQueryDTO articlePageQueryDTO) {
        PageHelper.startPage(articlePageQueryDTO.getPage(),articlePageQueryDTO.getPageSize());
        Page<Article> page = articleMapper.pageQuery(articlePageQueryDTO);
        return new PageResult(page.getTotal(),page.getResult());
    }

    /**
     * 修改文章
     * @param articleUpDTO
     */
    @Override
    public void update(ArticleUpDTO articleUpDTO) {
        Long id = articleUpDTO.getId();
        verify(id);
        Article articles = new Article();
        BeanUtils.copyProperties(articleUpDTO,articles);
        articleMapper.update(articles);
    }

    /**
     * 添加文章
     * @param articleInDTO
     */
    @Override
    public void insert(ArticleInDTO articleInDTO) {
        Article article = new Article();
        BeanUtils.copyProperties(articleInDTO,article);
        articleMapper.insert(article);
        sendWebSocket();
    }

    /**
     *
     * 发送实时数据
     */
    private void sendWebSocket(){
        Long articleCount = articleMapper.findByArticleCount();
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String,Object> objectMap = new HashMap<>(1);
        objectMap.put("articleCount",articleCount);
        try {
            String article = objectMapper.writeValueAsString(objectMap);
            WebSocketMessage webSocketMessage = new WebSocketMessage();
            webSocketMessage.setType(MessageType.Real_Time_Statistics);
            webSocketMessage.setMessage(article);
            String json = objectMapper.writeValueAsString(webSocketMessage);
            webSocketServer.sendToAllAdmin(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除文章
     * @param id
     */
    @Override
    @Transactional(rollbackFor = {ArticleIDNotFoundException.class})
    public void delete(Long id) {
        verify(id);
        articleUserMapper.delete(id);
        commentMapper.delete(id);
        favoritesMapper.delete(id);
        historyMapper.delete(id);
        articleMapper.delete(id);
    }

    /**
     * 获取文章
     * @return
     */
    @Override
    public List<ArticleVO> findByPublicArticle() {
        List<ArticleVO> article = articleMapper.findByArticleList();
        return article;
    }

    /**
     * 登录获取文章
     * @return
     */
    @Override
    public List<ArticleLoginVO> findByArticleUserId() {
        Long userId = BaseContext.getCurrentId();
        User byUserId = userMapper.findByUserId(userId);
        if (byUserId == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        List<ArticleLoginVO> articleLogin = articleMapper.findByArticleUserId(userId);
        return articleLogin;
    }

    /**
     * 根据id获取文章
     * @param id
     * @return
     */
    @Override
    public ArticleLoginVO findByArticleId(Long id) {
        Long userId = BaseContext.getCurrentId();
        verify(id);
        ArticleLoginVO articleLoginVO = articleMapper.findByArticleIdSelect(id,userId);
        return articleLoginVO;
    }

    /**
     * 验证
     * @param id
     */
    private void verify(Long id){
        Article byArticleId = articleMapper.findByArticleId(id);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
    }

    /**
     * 公共文章根据id获取文章
     * @param id
     * @return
     */
    @Override
    public ArticleVO findByPublicArticleId(Long id) {
        Article byArticleId = articleMapper.findByArticleId(id);
        if (byArticleId == null){
            throw new ArticleIDNotFoundException(MessageConstant.STUDENT);
        }
        ArticleVO articleVO = new ArticleVO();
        BeanUtils.copyProperties(byArticleId,articleVO);
        return articleVO;
    }

    /**
     * 模糊查询文章标题
     * @param title
     * @return
     */
    @Override
    public List<ArticleVO> findByPublicSearchArticle(String title) {
        ArticlePageQueryDTO articlePageQueryDTO = new ArticlePageQueryDTO();
        articlePageQueryDTO.setPage(1);
        articlePageQueryDTO.setPageSize(10);
        articlePageQueryDTO.setTitle(title);
        PageHelper.startPage(articlePageQueryDTO.getPage(),articlePageQueryDTO.getPageSize());
        Page<Article> articles = articleMapper.pageQuery(articlePageQueryDTO);
        List<ArticleVO> articleList = new ArrayList<>();
        for (Article article : articles){
            ArticleVO articleVO = ArticleVO.builder()
                    .id(article.getId())
                    .title(article.getTitle())
                    .build();
            articleList.add(articleVO);
        }
        return articleList;
    }
}
