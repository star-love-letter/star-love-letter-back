<template>
  <div class="login_container">
    <div class="content">
      <div class="title">注册</div>
      <div class="login_box">
        <el-form ref="RegisterFromRef" :model="RegisterFrom" :rules="RegisterFromRules" class="e_form">
          <!--    邮箱    -->
          <el-form-item prop="email">
            <el-input v-model="RegisterFrom.email" @change="getVerifyImage" prefix-icon="fas fa-lock"
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
          <!--    图片验证码    -->
          <el-form-item prop="imgCode" style="position: relative">
            <el-input v-model="RegisterFrom.imgCode" prefix-icon="fas fa-lock" placeholder="请输入右侧验证码"></el-input>
            <div v-if="verifyImage===''" class="getCodeDiv" @click="getVerifyImage">获取验证码</div>
            <img v-else :src="verifyImage" alt="" @click="getVerifyImage" style="cursor: pointer" title="点击刷新">
          </el-form-item>
          <!--    邮箱验证码    -->
          <el-form-item prop="emailCode">
            <el-input v-focus v-model="RegisterFrom.emailCode" prefix-icon="fas fa-user"
                      placeholder="请输入邮箱验证码"></el-input>
            <el-button type="info" @click='getEmailCode'>获取验证码</el-button>
          </el-form-item>

          <!--    按钮区域    -->
          <el-form-item class="btns">
            <el-button type="info" @click="Register" class="registerButton">注册</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      verifyImage: '',
      RegisterFrom: {
        userName: '',
        password: '',
        passwordAgain: '',
        imgCode: '',
        email: '',
        emailCode: '',
        show: true
      },
      // 表单验证
      RegisterFromRules: {
        email: [
          {required: true, message: '请输入邮箱', trigger: 'blur'},
          {type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change']}
        ],
        password: [
          {required: true, message: '请输入密码', trigger: 'blur'}
        ],
        userName: [
          {required: true, message: '请输入用户名称', trigger: 'blur'}
        ],
        passwordAgain: [
          {required: true, message: '请再次输入密码', trigger: 'blur'}
        ],
        imgCode: [
          {required: true, message: '请输入图片验证码', trigger: 'blur'}
        ],
        emailCode: [
          {required: true, message: '请输入邮箱验证码', trigger: 'blur'}
        ],
      },
    }
  },
  methods: {
    //注册按钮
    Register() {
      this.getVerifyImage()

      this.$refs.RegisterFromRef.validate(async (valid) => {
        if (!valid) {
          this.$message.error('账号或密码格式错误 请重新输入');
          this.$refs.RegisterFromRef.resetFields()
          return
        }

        if (this.password !== this.passwordAgain) {
          this.$message.error("两次输入密码不正确")
          this.password = ''
          this.passwordAgain = ''
          return
        }

        await this.$http.post('/api/user/add', {
          email: this.RegisterFrom.email,
          password: this.RegisterFrom.password,
          name: this.RegisterFrom.userName,
          imageCode: this.RegisterFrom.imgCode,
          emailCode: this.RegisterFrom.emailCode
        }).then((data) => {
          this.$message.success(data.message)
          this.$router.push('./Login')
        })
      })
    },
    //获取邮箱验证码
    async getEmailCode() {
      await this.$http.get('/api/user/verifyEmail', {
        imageCode: this.RegisterFrom.imgCode,
        email: this.RegisterFrom.email
      }).then((data) => {
        this.$message.success(data.message)
        this.getVerifyImage()
      })
    },
    //获取图片验证码
    async getVerifyImage() {
      if (this.RegisterFrom.email === '') {
        this.$message.warning("请输入邮箱后获取验证码");
        return
      }

      await this.$http.get('/api/user/verifyImage', {
        email: this.RegisterFrom.email
      }).then((data) => {
        this.verifyImage = data.data;
      }).catch((res) => {
        this.verifyImage = ''
      })
    }
  },
  created() {
    // this.getVerifyImage()
  }
}
</script>

<style scoped>
/deep/ .el-form-item__content {
  display: flex;
}

.title {
  margin-top: 8%;
  margin-bottom: 10px;
  text-align: center;
  color: white;
  font-size: 25px;
  font-weight: bold;
}

.content {
  margin: 0 auto;
  width: 60%;
  height: 100%;
  overflow: hidden;
}

.login_container {
  background-color: #6e79b8;
  height: 100%;
}

.login_box {
  width: 70%;
  height: 500px;
  background-color: #fff;
  border-radius: 20px;
  margin: 0 auto;
  display: flex;
  align-items: center;
  /*justify-content: center;*/
  /*position: relative;*/
}

.e_form {
  width: 70%;
  margin: 0 auto;
  padding-top: 20px;
}

.el-input {
  margin-right: 5px;
}

.registerButton {
  width: 100%;
}

.getCodeDiv {
  width: 80px;
  height: 41px;
  background-color: #bfa;
  font-size: 12px;
  text-align: center;
  cursor: pointer;
}
</style>
