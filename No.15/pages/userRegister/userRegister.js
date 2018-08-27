const app = getApp()

Page({
    data: {

    },

    doRegist: function(e) {
      var me = this;
      var formObject = e.detail.value;
      var username = formObject.username;
      var password = formObject.password;

      // 简单验证
      if (username.length == 0 || password.length == 0) {
        wx.showToast({
          title: '用户名或密码不能为空',
          icon: 'none',
          duration: 3000
        })
      }
    },
    goLoginPage:function(e){
      console.log("跳转到登录");
    }
})