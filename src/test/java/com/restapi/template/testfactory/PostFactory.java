package com.restapi.template.testfactory;

import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PostFactory extends AccountFactory {

  @Autowired
  protected PostRepository postRepository;

  /**
   * Post 생성.
   *
   * @param index index
   * @return 생성된 Post
   */
  @Transactional
  public Post generatePost(int index) {
    return this.postRepository.save(
        new Post(
            null,
            generateUserAndGetUser(index),
            "게시글" + index,
            "게시글 본문입니다."
        ));
  }
}
