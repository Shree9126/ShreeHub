package com.mindnotix.network;

import com.mindnotix.model.BasicResponse;
import com.mindnotix.model.DataEmailVerify;
import com.mindnotix.model.DataForgotPasswordRequest;
import com.mindnotix.model.DataLoginAuthorize;
import com.mindnotix.model.DataMasterInfo;
import com.mindnotix.model.DataRegSumbitResult;
import com.mindnotix.model.DataUploadProfilePic;
import com.mindnotix.model.NotificationCount;
import com.mindnotix.model.SubPartnerType.SubPartner;
import com.mindnotix.model.contactsupport.add_ticket.DataTicketRaiseITem;
import com.mindnotix.model.contactsupport.list.DataContactSupportFilter;
import com.mindnotix.model.contactsupport.master.DataContactSupportMaster;
import com.mindnotix.model.contactsupport.reply.DataTicketReplyITem;
import com.mindnotix.model.contactsupport.ticket_cancel.DataTicketCancelItem;
import com.mindnotix.model.contactsupport.view.DataContactSupportTicketVIew;
import com.mindnotix.model.dashboard.DataDashboardTimeLine;
import com.mindnotix.model.dashboard.image_upload.DataMultiImgSingleImgUpload;
import com.mindnotix.model.dashboard.like_comments_contribute.DataLikeCommentsContriubes;
import com.mindnotix.model.dashboard.tags.addtags.DataAddNewTag;
import com.mindnotix.model.dashboard.tags.taglist.DataPostTagList;
import com.mindnotix.model.events.attend_list.DataCounMeInAttendList;
import com.mindnotix.model.events.count_me_in.DataCountMeInOutItems;
import com.mindnotix.model.events.discussion_board.event_discuss_add.DataEventDiscussionBoardAdd;
import com.mindnotix.model.events.discussion_board.event_discuss_remove.DataEventDiscussionBoardRemove;
import com.mindnotix.model.events.discussion_board.list.DataEventDiscussinBoardList;
import com.mindnotix.model.events.list.DataEventsListItems;
import com.mindnotix.model.events.views.DataEventViewDetailsItem;
import com.mindnotix.model.explore.discussionboard.add.DataExploreDiscussionBoardAdd;
import com.mindnotix.model.explore.discussionboard.list.DataExploreDiscussionBoardList;
import com.mindnotix.model.explore.discussionboard.remove.DataExploreDiscussionBoardRemove;
import com.mindnotix.model.explore.explorerView.ExplorerBasicView;
import com.mindnotix.model.explore.filter_master.DataExploreFilterItems;
import com.mindnotix.model.explore.list.DataExploreListItems;
import com.mindnotix.model.explore.my_explore_add.DataMyExploreAddItems;
import com.mindnotix.model.explore.my_explore_remove.DataMyExploreRemove;
import com.mindnotix.model.explore.rating.DataExploreRatingItems;
import com.mindnotix.model.find_connection.list.DataFindConnectionList;
import com.mindnotix.model.find_connection.master.DataFindConnectionMaster;
import com.mindnotix.model.find_connection.master.share.DataFindNavigatorsShareMaster;
import com.mindnotix.model.find_connection.master.sub_service_type.DataSubServiceTypeItems;
import com.mindnotix.model.jobs.apply_master.DataJobApplyMaster;
import com.mindnotix.model.jobs.filter_master.DataJobMasterFilter;
import com.mindnotix.model.jobs.filter_master.local_board.DataLocalBoardList;
import com.mindnotix.model.jobs.filter_master.sub_category.DataJobSubCategory;
import com.mindnotix.model.jobs.jobview.DataJIbDetailsView;
import com.mindnotix.model.jobs.list.DataJobListItems;
import com.mindnotix.model.message.add_message.DataAddMessageItems;
import com.mindnotix.model.message.chat_history.DataChatMessageHistoryItems;
import com.mindnotix.model.message.chat_messages_list.DataChatMessageListItems;
import com.mindnotix.model.message.user_list.DataMessageUserListItems;
import com.mindnotix.model.notification.DataNotificationsItems;
import com.mindnotix.model.profile.DataProfileGalleryItems;
import com.mindnotix.model.resume.Education.Education;
import com.mindnotix.model.resume.Education.provider.Provider;
import com.mindnotix.model.resume.Education.qualificationSubCatgory.QualifiactionSubCatagory;
import com.mindnotix.model.resume.Education.title.Title;
import com.mindnotix.model.resume.extraActivites.ExtraActivities;
import com.mindnotix.model.resume.loadResume.Resume;
import com.mindnotix.model.resume.volunteer.Volunteer;
import com.mindnotix.model.resume.volunteer.volunteerEdit.VolunteerEdit;
import com.mindnotix.model.resume.workExperience.Example;
import com.mindnotix.model.resume.workExperience.keyresponsibiltiesConfirmation.DeleteConfirmation;
import com.mindnotix.model.resume.workExperience.workExperienceEdit.WorkExperienceEdit;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
//import rx.Observable;

/**
 * Created by Sridharan on 1/18/2018.
 */

public interface YouthHubApi {

/*    @Headers({
            "Authorization: Basic YWRtaW46Q2hBVCpNaU5EQDE3",
            "Accept: application/json"
    })
    @GET("restapi/v1/users")
    Call<BasicResponse> getusers();

    @Headers({
            "Authorization: Basic YWRtaW46Q2hBVCpNaU5EQDE3",
            "Content-Type: application/json"
    })
    @POST("restapi/v1/users")
    Call<Void> postusers(@Body RegisterRequest body);*/


    @POST("authorize")
    @FormUrlEncoded
    Call<DataLoginAuthorize> getAuthorizeLogin(@FieldMap Map<String, String> options);

    @POST("checkemail")
    @FormUrlEncoded
    Call<BasicResponse> getCheckEmailExist(@FieldMap Map<String, String> options);

    @POST("userregisterrequest")
    @FormUrlEncoded
    Call<DataEmailVerify> getEmailVerification(@FieldMap Map<String, String> options);

    @POST("userregistercodeverify")
    @FormUrlEncoded
    Call<DataEmailVerify> getEmailVerificationPasscode(@FieldMap Map<String, String> options);

    @POST("forgetpasswordrequest")
    @FormUrlEncoded
    Call<DataForgotPasswordRequest> getForgotPassRequest(@FieldMap Map<String, String> options);

/*
    @POST("forgetpasswordrequest")
    @FormUrlEncoded
    Observable<Response<ResponseBody>> getForgotPassRequest(@FieldMap Map<String, String> options);
*/

    @POST("forgetpasswordcodeverify")
    @FormUrlEncoded
    Call<DataEmailVerify> getForgotPassCodeVerify(@FieldMap Map<String, String> options);

    @POST("forgetpassword")
    @FormUrlEncoded
    Call<BasicResponse> getResetPassword(@FieldMap Map<String, String> options);


    /*    @GET("getposttags")
        Observable<Response<ResponseBody>> getPostTagList(@Header("Authorizations") String value);*/
    @GET("getposttags")
    Call<DataPostTagList> getPostTagList(@Header("Authorizations") String value);

/*    @POST("newposttag")
    @FormUrlEncoded
    Observable<Response<ResponseBody>> postNewTag(@Header("Authorizations") String value,@FieldMap Map<String, String> options);*/

    @POST("newposttag")
    @FormUrlEncoded
    Call<DataAddNewTag> postNewTag(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


/*
    @POST("newpost")
    @FormUrlEncoded
    Observable<Response<ResponseBody>> submitNewPost(@Header("Authorizations") String value,@FieldMap Map<String, String> options);
*/

    @POST("newpost")
    @FormUrlEncoded
    Call<DataDashboardTimeLine> submitNewPost(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("postupdate")
    @FormUrlEncoded
    Call<DataDashboardTimeLine> updatePost(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("updateposts")
    @FormUrlEncoded
    Call<BasicResponse> AddToMyStept(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("passwordchange")
    @FormUrlEncoded
    Call<DataEmailVerify> getChangePassword(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("getlocalboard")
    @FormUrlEncoded
    Call<DataEmailVerify> getLocalBoardDistrict(@FieldMap Map<String, String> options);


    @POST("userregister")
    @FormUrlEncoded
    Call<DataRegSumbitResult> submitData(@FieldMap Map<String, String> options);


    @GET("userregistermaster")
    Call<DataMasterInfo> getMasterData();


/*    @Multipart
    @POST("userprofilepicupload")
    Observable<Response<ResponseBody>> uploadMUCProfileImagePost(@Part MultipartBody.Part file);*/


    @Multipart
    @POST("userprofilepicupload")
    Call<DataUploadProfilePic> uploadMUCProfileImagePosts(@Part MultipartBody.Part file);


/*    @POST("getposts")
    @FormUrlEncoded
    Observable<Response<ResponseBody>> getTimeLineostLists(@Header("Authorizations") String value, @FieldMap Map<String, String> options);*/

    @POST("getposts")
    @FormUrlEncoded
    Call<DataDashboardTimeLine> getTimeLinePostList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("postsnewtweak")
    @FormUrlEncoded
    Call<DataLikeCommentsContriubes> getLikesCommentsContribute(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("postsdeletetweak")
    @FormUrlEncoded
    Call<DataLikeCommentsContriubes> getDislike(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

/*
    @POST("imgupload")
    @FormUrlEncoded
    Call<DataMultiImgSingleImgUpload> uploadImage(@Header("Authorizations") String value, @FieldMap Map<String, ArrayList<String>> options);
*/

    @POST("imgupload")
    Call<DataMultiImgSingleImgUpload> uploadImage(@Header("Authorizations") String value, @Body RequestBody file);

    @POST("getpoststweaks")
    @FormUrlEncoded
    Call<DataLikeCommentsContriubes> getpoststweaks(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("postsdeletetweak")
    @FormUrlEncoded
    Call<DataLikeCommentsContriubes> getPostCommentDelete(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("postsnewtweak")
    @FormUrlEncoded
    Call<DataLikeCommentsContriubes> getpostsnewtweaks(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("postsdelete")
    @FormUrlEncoded
    Call<BasicResponse> deletePost(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    //JOBS
    @POST("jobslist")
    @FormUrlEncoded
    Call<DataJobListItems> getJobLists(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("jobfavourites")
    @FormUrlEncoded
    Call<BasicResponse> jobFavourite(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("jobsfiltermaster")
    Call<DataJobMasterFilter> getJobMasterFilter(@Header("Authorizations") String value);


    @POST("getlocalboard")
    @FormUrlEncoded
    Call<DataLocalBoardList> getLocalBoardList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("getjobsubcategory")
    @FormUrlEncoded
    Call<DataJobSubCategory> getSubCategory(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("jobsview")
    @FormUrlEncoded
    Call<DataJIbDetailsView> getJobDetailsView(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("jobsapplymaster")
    @FormUrlEncoded
    Call<DataJobApplyMaster> getJobApplyMaster(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("jobsapply")
    @FormUrlEncoded
    Call<BasicResponse> putApplyJobSubmit(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("myjobs")
    Call<BasicResponse> loadMyJobs(@Header("Authorizations") String value);

    //Events
    @POST("eventfilter")
    @FormUrlEncoded
    Call<DataEventsListItems> getEventsList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("countmeevent")
    @FormUrlEncoded
    Call<DataCountMeInOutItems> getCountMeInOut(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("viewevent")
    @FormUrlEncoded
    Call<DataEventViewDetailsItem> getEventDetialsData(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("eventdiscussionboard")
    @FormUrlEncoded
    Call<DataEventDiscussinBoardList> getDiscussionBoardList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("eventdiscussionboardremove")
    @FormUrlEncoded
    Call<DataEventDiscussionBoardRemove> getEventDiscussoinBoardRemove(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("countmelistevent")
    @FormUrlEncoded
    Call<DataCounMeInAttendList> getEventParticipantsLists(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("eventdiscussionboardadd")
    @FormUrlEncoded
    Call<DataEventDiscussionBoardAdd> addDiscussionBoardMessage(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

//Explore

    @POST("ratingexplore")
    @FormUrlEncoded
    Call<DataExploreRatingItems> getExploreRating(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("explorefiltermaster")
    Call<DataExploreFilterItems> getExploreMasterFilter(@Header("Authorizations") String value);

    @POST("explorefilter")
    @FormUrlEncoded
    Call<DataExploreListItems> getLoadExploreData(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("viewexplore")
    @FormUrlEncoded
    Call<ExplorerBasicView> loadExplorerView(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("discussionboard")
    @FormUrlEncoded
    Call<DataExploreDiscussionBoardList> getExploreDiscussionBoardList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("discussionboardremove")
    @FormUrlEncoded
    Call<DataExploreDiscussionBoardRemove> getExploreDiscussionBoardRemove(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("addmyexplore")
    @FormUrlEncoded
    Call<DataMyExploreAddItems> getMyExploreAdd(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("removemyexplore")
    @FormUrlEncoded
    Call<DataMyExploreRemove> getMyExploreRemove(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("discussionboardadd")
    @FormUrlEncoded
    Call<DataExploreDiscussionBoardAdd> getExploreDiscussionBoardAdd(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

/*
    @GET("jobsfiltermaster")
    Call<DataExploreFilterItems> getExploreMasterFilter(@Header("Authorizations") String value);*/
    //Profile

    @GET("youth/getprofile")
    Call<BasicResponse> loadProfile(@Header("Authorizations") String value,
                                    @QueryMap Map<String, String> options);

    @Multipart
    @POST("profilepicuploadwithid")
    Call<DataUploadProfilePic> uploadProfileImage(@Header("Authorizations") String value, @Part MultipartBody.Part file);

    @Multipart
    @POST("usercoverpicupload")
    Call<DataUploadProfilePic> uploadCoverImage(@Header("Authorizations") String value, @Part MultipartBody.Part file);


    @POST("userpicdelete")
    @FormUrlEncoded
    Call<BasicResponse> profilePictureDelete(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/deletewishlist")
    @FormUrlEncoded
    Call<BasicResponse> deleteWhishlist(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/updateyouthprofile")
    @FormUrlEncoded
    Call<BasicResponse> updateProfile(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("inviteusers")
    @FormUrlEncoded
    Call<BasicResponse> inviteUsers(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("inviteusersmaster")
    Call<BasicResponse> inviteUsersDilaog(@Header("Authorizations") String value);


    @GET("supportrequest")
    Call<BasicResponse> inviteUsersTitle(@Header("Authorizations") String value);

    @GET("youth/getwishlistmaster")
    Call<BasicResponse> loadWishListMaster(@Header("Authorizations") String value);

    @POST("youth/addwishlist")
    @FormUrlEncoded
    Call<BasicResponse> addWhishlistMaster(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("getsuburbs")
    @FormUrlEncoded
    Call<DataEmailVerify> getSurubs(@FieldMap Map<String, String> options);


    //Contact Support


    @POST("cancelsupport")
    @FormUrlEncoded
    Call<DataTicketCancelItem> cancelTicket(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("replysupport")
    @FormUrlEncoded
    Call<DataTicketReplyITem> replyTicket(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("supportfiltermaster")
    Call<DataContactSupportMaster> getSupportFilterMaster(@Header("Authorizations") String value);

    @POST("supportfilter")
    @FormUrlEncoded
    Call<DataContactSupportFilter> getContactSupportList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("addsupport")
    @FormUrlEncoded
    Call<DataTicketRaiseITem> sumbitTicketCreate(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("viewsupport")
    @FormUrlEncoded
    Call<DataContactSupportTicketVIew> getTicketDetails(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @Multipart
    @POST("uploadsupportimage")
    Call<DataUploadProfilePic> uploadSupportImage(@Header("Authorizations") String value, @Part MultipartBody.Part file);


    //Gallery
    @POST("youth/gallerylist")
    @FormUrlEncoded
    Call<DataProfileGalleryItems> getGalleryList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("youth/getemploymentsmaster")
    Call<Example> loadMaster(@Header("Authorizations") String value);

    @POST("youth/getpartnersubtypes")
    @FormUrlEncoded
    Call<SubPartner> getPartnerSubId(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("youth/addemployment")
    @FormUrlEncoded
    Call<BasicResponse> addEmployement(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("youth/getresume")
    Call<Resume> loadResume(@Header("Authorizations") String value, @QueryMap Map<String, String> data);

    //Find Connections
    @POST("findconnectionlist")
    @FormUrlEncoded
    Call<DataFindConnectionList> getFindConnectionList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("findconnectionmaster")
    Call<DataFindConnectionMaster> getFindConnectionMasterData(@Header("Authorizations") String value);

    @POST("sendmessagetouser")
    @FormUrlEncoded
    Call<BasicResponse> sendContactMessage(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("getjobsubcategory")
    @FormUrlEncoded
    Call<DataSubServiceTypeItems> getSubServiceTypeBusiness(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("youth/geteducationmaster")
    Call<Education> loadEduactionMaster(@Header("Authorizations") String value);

    @POST("youth/getqualificationprovider")
    @FormUrlEncoded
    Call<Provider> loadProviderData(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    //Notifications

    @POST("notification")
    @FormUrlEncoded
    Call<DataNotificationsItems> getNotifications(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("shareprofile")
    @FormUrlEncoded
    Call<BasicResponse> shareNavigators(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("updatefollowersfollowing")
    @FormUrlEncoded
    Call<BasicResponse> updateFollowUnFollowStatus(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("shareprofilemaster")
    Call<DataFindNavigatorsShareMaster> getFindMasterShare(@Header("Authorizations") String value);

//Notification

    @GET("notificationcount")
    Call<NotificationCount> getNotificationCount(@Header("Authorizations") String value);


    //Message


    @POST("messageuserfilter")
    @FormUrlEncoded
    Call<DataMessageUserListItems> getMessageUserList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("messagechathistory")
    @FormUrlEncoded
    Call<DataChatMessageHistoryItems> getChatHistoryMessageList(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("listmessage")
    @FormUrlEncoded
    Call<DataChatMessageListItems> getChatMessageBasedOnChatID(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("addmessage")
    @FormUrlEncoded
    Call<DataAddMessageItems> sendMessages(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    //CV Resume

    @POST("youth/deletequalification")
    @FormUrlEncoded
    Call<Resume> deleteWork(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("youth/getqualification")
    @FormUrlEncoded
    Call<WorkExperienceEdit> loadQualification(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @GET("responsibilitiesdeleteconf")
    Call<DeleteConfirmation> DeleteConfirmation(@Header("Authorizations") String value);


    @POST("responsibilitiesdelete")
    @FormUrlEncoded
    Call<BasicResponse> deleteKeyResponsibiltes(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/editemployment")
    @FormUrlEncoded
    Call<BasicResponse> UpdateEmployement(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/getqualificationtitle")
    @FormUrlEncoded
    Call<Title> loadQualificationTitle(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/getqualificationsubcategory")
    @FormUrlEncoded
    Call<QualifiactionSubCatagory> loadQualificationSubcatagory(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/addeducation")
    @FormUrlEncoded
    Call<BasicResponse> addEducationApi(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/deleteextraactivities")
    @FormUrlEncoded
    Call<ExtraActivities> deleteExtraActivities(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/deletequalification")
    @FormUrlEncoded
    Call<Resume> deleteQualification(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @GET("youth/getvolunteermaster")
    Call<Volunteer> loadCause(@Header("Authorizations") String value);


    @POST("youth/addvolunteer")
    @FormUrlEncoded
    Call<Volunteer> addVolunter(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/getqualification")
    @FormUrlEncoded
    Call<VolunteerEdit> editVolunteer(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("youth/updatevolunteer")
    @FormUrlEncoded
    Call<VolunteerEdit> updateVolunteer(@Header("Authorizations") String value, @FieldMap Map<String, String> options);


    @POST("youth/addextraactivities")
    @FormUrlEncoded
    Call<Volunteer> addTechnicalSkills(@Header("Authorizations") String value, @FieldMap Map<String, String> options);

    @POST("youth/updateextraactivities")
    @FormUrlEncoded
    Call<Volunteer> updateTechnicalSkills(@Header("Authorizations") String value, @FieldMap Map<String, String> options);
}


