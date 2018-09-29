package com.run.conifg;

public class RxBusConfig {

    public static final int VEDIO_TYPE = 211;
    public static final int VEDIO_LANDSCAPE = 1;//横屏
    public static final int VEDIO_PORTRAIT = 2;//竖屏

    public static final int VEDIO_PLAY_TYPE = 221;
    public static final int VEDIO_REPLAY_CODE = 1;//重新播放



    public static class LoginConfig {
        public final static int Login_Type = 6000; //登录
        public final static int Logout_Type = 7000;//退出登录
        public final static int Modify_Image_Type = 8000;//修改信息
        public final static int Modify_Bg_Type = 8800;//修改背景
        public final static int Upload_Image_Type = 9000;//修改信息

        public final static int Login_Dialog = 6011;//登录弹窗

        public final static int Success_Code = 1;//登录成功
        public final static int Fali_Code = 2;//登录失败
        public final static int Cancle_Code = 3;//取消登录
        public final static int BindMobile_Code = 4;//绑定手机
    }

    public static class ArticeConfig {
        public final static int Refalsh_Type = 2000;
        public final static int Ding_Code = 1;
        public final static int Cai_Code = 2;
        public final static int Comment_Code = 3;
        public final static int Ding_Code_Cancle_ = 4;
    }

}
