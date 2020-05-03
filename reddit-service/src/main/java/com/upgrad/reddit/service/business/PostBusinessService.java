package com.upgrad.reddit.service.business;


import com.upgrad.reddit.service.dao.PostDao;
import com.upgrad.reddit.service.dao.UserDao;
import com.upgrad.reddit.service.entity.PostEntity;
import com.upgrad.reddit.service.entity.UserAuthEntity;
import com.upgrad.reddit.service.entity.UserEntity;
import com.upgrad.reddit.service.exception.AuthorizationFailedException;
import com.upgrad.reddit.service.exception.InvalidPostException;
import com.upgrad.reddit.service.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;

@Service
public class PostBusinessService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public PostEntity createPost(PostEntity postEntity, String authorization) throws AuthorizationFailedException {

        UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);
        postDao.createPost(postEntity);
        return  postEntity;
    }

    public TypedQuery<PostEntity> getPosts(String authorization) throws AuthorizationFailedException {

        UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);
        return postDao.getPosts();

    }



    @Transactional(propagation = Propagation.REQUIRED)
    public PostEntity editPostContent(PostEntity postEntity, String postId, String authorization) throws AuthorizationFailedException, InvalidPostException {
        UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);
        postDao.editPost(postEntity);
        return postEntity;
    }



    @Transactional(propagation = Propagation.REQUIRED)

        public PostEntity deletePost(String postId, String authorization) throws AuthorizationFailedException, InvalidPostException {
         UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);
           PostEntity postEntity=postDao.getPostByUuid(postId);
          postDao.deletePost(postEntity);

        return postEntity;
    }


    public TypedQuery<PostEntity> getPostsByUser(String userId, String authorization) throws AuthorizationFailedException, UserNotFoundException {
        UserAuthEntity userAuthEntity = userDao.getUserAuthByAccesstoken(authorization);
        PostEntity  postEntity=postDao.getPostByUuid(userId);
        return postDao.getPostsByUser(userAuthEntity.getUser());
    }
}
