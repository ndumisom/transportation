package za.com.cocamzansi.controller;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import za.com.cocamzansi.entity.UserEntity;

@Entity
@Table(name = "user_details")
public class UserDetailsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "user_details_id")
  private Integer userDetailsId;
  
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserEntity userId;
  
  @Column(name = "user_profile_picture")
  private String userProfilePicture;
  
  public Integer getUserDetailsId() {
    return this.userDetailsId;
  }
  
  public void setUserDetailsId(Integer userDetailsId) {
    this.userDetailsId = userDetailsId;
  }
  
  public UserEntity getUserId() {
    return this.userId;
  }
  
  public void setUserId(UserEntity userId) {
    this.userId = userId;
  }
  
  public String getUserProfilePicture() {
    return this.userProfilePicture;
  }
  
  public void setUserProfilePicture(String userProfilePicture) {
    this.userProfilePicture = userProfilePicture;
  }
}
