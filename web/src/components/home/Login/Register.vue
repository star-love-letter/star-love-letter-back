<template>
  <div class="login_container">
    <div class="content">
      <div class="title">注册</div>
      <div class="login_box">
        <el-form ref="RegisterFromRef" :model="RegisterFrom" :rules="RegisterFromRules" class="e_form">
          <!--    邮箱    -->
          <el-form-item prop="email">
            <el-input v-model="RegisterFrom.email" prefix-icon="fas fa-lock"
                      placeholder="请输入邮箱"></el-input>
          </el-form-item>
          <!--    用户名称    -->
          <el-form-item prop="userName">
            <el-input v-focus v-model="RegisterFrom.userName" prefix-icon="fas fa-user"
                      placeholder="请输入用户名称"></el-input>
          </el-form-item>
          <!--    密码    -->
          <el-form-item prop="password">
            <el-input type="password" v-model="RegisterFrom.password" prefix-icon="fas fa-lock"
                      placeholder="请输入密码"></el-input>
          </el-form-item>
          <!--    重复密码    -->
          <el-form-item prop="passwordAgain">
            <el-input type="password" v-model="RegisterFrom.passwordAgain" prefix-icon="fas fa-lock"
                      placeholder="请再次输入密码"></el-input>
          </el-form-item>
          <!--    按钮区域    -->
          <el-form-item class="btns">
            <!--     按钮弹出框       -->
            <el-popover
              placement="top"
              width="400"
              trigger="manual"
              v-model="showPopper">
              <div class="popover-container" v-if="showRotateCode === true">
                <div class="popover-close" @click="showPopper = false">+</div>
                <div class="popover-title">
                  <div class="popover-title-top">
                    {{popoverTitle}}
                  </div>
                </div>
                <Slider :mouseupFun="getEmailCode" :status="rangeStatus" :img-url="rotateCodeUrl"
                        @rotateAngle='(data)=> rotateAngle = parseInt(data)'></Slider>
              </div>
              <div class="popover-container popover-emailCode" v-else>
                <div class="popover-close" @click="showPopper = false">+</div>
                <div class="popover-title-top">
                  {{popoverTitle}}
                </div>
                <el-input
                  placeholder="请输入邮箱验证码"
                  v-model="RegisterFrom.emailCode"
                  clearable>
                </el-input>
                <div class="rotate-code-button">
                  <el-button @click="Register" type="primary">完成注册</el-button>
                </div>
              </div>
              <!--   获取图片验证码   -->
              <el-button slot="reference" disabled type="primary"
                         style="width: 400px" v-if="showCountDownRotateCode === true">
                {{getRotateCodeBtnTxt+'('+countDown+')'}}
              </el-button>
              <el-button slot="reference" @click="getRotateCode" type="primary"
                         style="width: 400px" v-else>{{getRotateCodeBtnTxt}}
              </el-button>
            </el-popover>
          </el-form-item>
        </el-form>
        <router-link to="/Login" class="go-login">已经有账号？去登陆</router-link>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    data() {
      var passwordAgain = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'));
        } else if (value !== this.RegisterFrom.password) {
          callback(new Error('两次输入密码不一致!'));
        } else {
          callback();
        }
      };
      return {
        //判断图片是否为正
        rangeStatus: false,
        timer: null,
        // status: false,
        //倒计时
        countDown: 60,
        //是否显示倒计时获取邮箱按钮
        showCountDownRotateCode: false,
        //获取邮箱验证码的按钮文字
        getRotateCodeBtnTxt: '获取邮箱验证码',
        //弹出框的标题
        popoverTitle: '获取邮箱验证码',
        //是否显示旋转验证码
        showRotateCode: true,
        //显示弹出框
        showPopper: false,
        //旋转度数
        rotateAngle: 0,
        //图片base64
        rotateCodeUrl: '',
        RegisterFrom: {
          userName: '',
          password: '',
          passwordAgain: '',
          email: '',
          emailCode: ''
        },
        // 表单验证
        RegisterFromRules: {
          email: [
            {required: true, message: '请输入邮箱', trigger: 'change'},
            {type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur'}
          ],
          password: [
            {required: true, message: '请输入密码', trigger: 'change'},
            {min: 7, message: '密码必须大于6个字', trigger: 'blur'},
            {max: 17, message: '密码必须小于18个字', trigger: 'blur'}
          ],
          userName: [
            {required: true, message: '请输入用户名称', trigger: 'change'},
            {max: 8, message: '名称不能超过8个字', trigger: 'blur'}
          ],
          passwordAgain: [
            {validator: passwordAgain, trigger: 'change'}
          ],
          emailCode: [
            {required: true, message: '请输入邮箱验证码', trigger: 'change'}
          ],
        },
      }
    },
    methods: {
      //获取邮箱验证码
      async getEmailCode() {
        this.rangeStatus = false;
        await this.$http.get('/api/user/emailCode', {
          email: this.RegisterFrom.email,
          angle: this.rotateAngle
        }).then((data) => {
          this.$message.success(data.message);
          this.rangeStatus = true;
          setTimeout(() => {
            this.showRotateCode = false;
            this.popoverTitle = '请输入邮箱验证码';
            this.showCountDownRotateCode = true;
            this.timer = setInterval(() => {
              if (this.countDown > 0) {
                this.countDown -= 1;
              } else {
                clearInterval(this.timer);
                this.getRotateCodeBtnTxt = '重新获取邮箱验证码';
                this.showCountDownRotateCode = false;
              }
            }, 1000)
          }, 1000);
        }).catch(() => {
          this.rotateAngle = 0;
          this.rangeStatus = false;
          this.getRotateCode();
        })
      },
      //获取旋转验证码
      async getRotateCode() {
        this.rangeStatus = false;
        this.countDown = 60;
        this.popoverTitle = '获取邮箱验证码';
        this.$refs.RegisterFromRef.validate(async (valid) => {
          if (valid) {
            await this.$http.get('/api/user/rotateCode', {
              email: this.RegisterFrom.email
            }).then((data) => {
              this.rotateCodeUrl = data.data;
              this.showPopper = true
            });
            this.rotateAngle = 0;
            this.showRotateCode = true
          }
        });
        this.RegisterFrom.emailCode = ''
      },
      //注册按钮
      async Register() {
        if (this.RegisterFrom.emailCode === '') {
          this.$message.error('请输入邮箱验证码');
          return
        }

        await this.$http.post('/api/user/addByEmail', {
          email: this.RegisterFrom.email,
          password: this.RegisterFrom.password,
          name: this.RegisterFrom.userName,
          emailCode: this.RegisterFrom.emailCode
        }).then((data) => {
          this.$message.success(data.message);
          this.$router.push('./Login')
        })
      },
    },
  }
</script>

<style scoped>
  .title {
    margin-top: 80px;
    margin-bottom: 10px;
    text-align: center;
    color: white;
    font-size: 25px;
    font-weight: bold;
  }

  .content {
    margin: 0 auto;
    width: 850px;
    height: 100%;
    overflow: hidden;
  }

  .login_container {
    background-color: #FF7F50;
    height: 100%;
  }

  .login_box {
    width: 550px;
    height: 500px;
    background-color: #fff;
    border-radius: 20px;
    margin: 0 auto;
    display: flex;
    align-items: center;
    flex-direction: column;
    justify-content: center;
  }

  .e_form {
    width: 400px;
    margin: 0 auto;
    padding-top: 20px;
  }

  .popover-container {
    text-align: center;
    height: 280px;
    padding: 20px;
    position: relative;
  }

  .popover-emailCode {
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
  }

  .popover-title {
    text-align: center;
    font-size: 14px;
  }

  .popover-title-top {
    font-size: 16px;
    margin-bottom: 30px;
    font-weight: bold;
  }

  .popover-close {
    transform: rotate(45deg);
    position: absolute;
    top: -13px;
    right: 0;
    font-size: 30px;
    cursor: pointer;
  }

  .go-login {
    color: #999;
    text-decoration: none;
    border-bottom: 1px solid #fff;
  }

  .go-login:hover {
    border-bottom: 1px solid #999;
  }
</style>
