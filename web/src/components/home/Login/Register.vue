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
              :title="popoverTitle"
              width="400"
              trigger="manual"
              v-model="showPopper">
              <div class="popover-container" v-if="showRotateCode === true">
                <el-image class="rotate-code" :style="{transform:'rotate('+rotateAngle + 'deg)'}" :src="rotateCodeUrl"
                          fit="cover"></el-image>
                <el-slider v-model="sliderValue" :show-tooltip="false" max="90"
                           @change="sliderChange"></el-slider>
              </div>
              <div class="popover-container popover-emailCode" v-else>
                <el-input
                  placeholder="请输入邮箱验证码"
                  v-model="RegisterFrom.emailCode"
                  clearable>
                </el-input>
                <div class="rotate-code-button">
                  <el-button @click="Register" type="primary">完成注册</el-button>
                  <el-button @click="getRotateCode">重新获取邮箱验证码</el-button>
                </div>
              </div>
              <!--   获取图片验证码   -->
              <el-button slot="reference" @click="getRotateCode" type="primary" style="width: 400px">注册</el-button>
            </el-popover>


          </el-form-item>
        </el-form>
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
        flag: false,
        popoverTitle: '获取邮箱验证码，拖动滑块，使图片角度为正',
        //是否显示旋转验证码
        showRotateCode: true,
        //显示弹出框
        showPopper: false,
        //是否减角度
        minusAngle: false,
        //旋转度数
        rotateAngle: 0,
        //每次增加的度数
        everyAngle: 4,
        //图片base64
        rotateCodeUrl: '',
        //滑块的值
        sliderValue: '',
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
            {required: true, message: '请输入密码', trigger: 'change'}
          ],
          userName: [
            {required: true, message: '请输入用户名称', trigger: 'change'}
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
    watch: {
      //监听滑块数据的改变
      sliderValue: function (val, oldVal) {
        //第一次不执行
        if (this.flag) {
          if (oldVal > val && this.minusAngle) {
            this.rotateAngle -= this.everyAngle
          } else {
            this.rotateAngle += this.everyAngle;
            this.minusAngle = true
          }
        }
        this.flag = true
      }
    },
    methods: {
      //鼠标拖拽松开时触发
      async sliderChange() {
        await this.$http.get('/api/user/emailCode', {
          email: this.RegisterFrom.email,
          angle: this.rotateAngle
        }).then((data) => {
          this.$message.success(data.message);
          setTimeout(() => {
            this.showRotateCode = false;
            this.popoverTitle='请输入邮箱验证码'
          }, 1000)
        }).catch(() => {
          this.rotateAngle = 0;
          this.sliderValue = 0;
          this.flag = false;
          this.minusAngle = false;
          this.getRotateCode()
        })
      },
      //获取旋转验证码
      async getRotateCode() {
        this.$refs.RegisterFromRef.validate(async (valid) => {
          if (valid) {
            await this.$http.get('/api/user/rotateCode', {
              email: this.RegisterFrom.email
            }).then((data) => {
              this.rotateCodeUrl = data.data;
              this.showPopper = true
            });
            this.rotateAngle = 0;
            this.sliderValue = 0;
            this.flag = false;
            this.minusAngle = false;
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
  }

  .e_form {
    width: 400px;
    margin: 0 auto;
    padding-top: 20px;
  }

  .rotate-code {
    width: 150px;
    height: 150px;
    text-align: center;
  }

  .popover-container{
    text-align: center;
    height: 230px;
    padding: 20px;
  }

  .popover-emailCode{
    display: flex;
    flex-direction: column;
    justify-content: space-evenly;
  }
</style>
