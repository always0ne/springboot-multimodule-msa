package com.restapi.template.testfactory;

import com.restapi.template.api.community.comment.data.Comment;
import com.restapi.template.api.community.comment.data.CommentRepository;
import com.restapi.template.api.community.post.data.Post;
import com.restapi.template.api.community.post.data.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class CommentFactory extends AccountFactory {

  @Autowired
  protected PostRepository postRepository;
  @Autowired
  protected CommentRepository commentRepository;

  /**
   * post에 댓글 생성.
   *
   * @param post  댓글을 추가할 post
   * @param index index
   * @return SignInResponse
   */
  @Transactional
  public long addComment(Post post, int index) {
    Comment savedComment = this.commentRepository.save(
        new Comment(
            generateUserAndGetUser(index),
            index + "번째 댓글"
        )
    );
    post.addComment(savedComment);
    this.postRepository.save(post);
    return savedComment.getCommentId();
  }
}
