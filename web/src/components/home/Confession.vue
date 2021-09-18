<template>
  <div style="display: flex;align-items: center">
    <el-form :model="addLove" :rules="addLoveRules" ref="addLoveRef" label-width="100px">
      <h3>你的信息</h3>
      <el-form-item label="你的名字" prop="CName">
        <el-input v-model="addLove.CName" placeholder="你的名字" style="width: 70%;"
                  @keyup.enter.native="submitForm('addLoveRef')"></el-input>
      </el-form-item>
      <el-form-item label="你的性别" prop="CGender">
        <el-select v-model="addLove.CGender" placeholder="请选择性别" style="width: 70%;"
                   @keyup.enter.native="submitForm('addLoveRef')">
          <el-option label="男" value=1></el-option>
          <el-option label="女" value=2></el-option>
          <el-option label="保密" value=0></el-option>
        </el-select>
      </el-form-item>
      <h3>TA的信息</h3>
      <el-form-item label="TA的名字" prop="BeCName">
        <el-input v-model="addLove.BeCName" placeholder="你的名字" style="width: 70%;"
                  @keyup.enter.native="submitForm('addLoveRef')"></el-input>
      </el-form-item>
      <el-form-item label="TA的性别" prop="BeCGender">
        <el-select v-model="addLove.BeCGender" placeholder="请选择性别" style="width: 70%;"
                   @keyup.enter.native="submitForm('addLoveRef')">
          <el-option label="男" value=1></el-option>
          <el-option label="女" value=2></el-option>
          <el-option label="保密" value=0></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="表白内容" prop="CContent">
        <el-input type="textarea" :rows="5" v-model="addLove.CContent" placeholder="不得超过160个字" resize="none"
                  style="width: 300px"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="submitForm('addLoveRef')">立即创建</el-button>
        <el-button @click="resetForm('addLoveRef')">重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  data() {
    return {
      addLove: {
        CName: '',
        CGender: '',
        BeCName: '',
        BeCGender: '',
        CContent: ''
      },
      addLoveRules: {
        CName: [
          {required: true, message: '请输入你的名字', trigger: 'blur'},
          {max: 6, message: '长度不能超过 6 个字符', trigger: 'blur'}
        ],
        CGender: [
          {required: true, message: '请选择你的性别', trigger: 'change'}
        ],
        BeCName: [
          {required: true, message: '请输入TA的名字', trigger: 'change'}
        ],
        BeCGender: [
          {required: true, message: '请选择TA的性别', trigger: 'change'}
        ],
        CContent: [
          {required: true, message: '请输入表白内容', trigger: 'change'},
          {max: 160, message: '长度不能超过 160 个字符', trigger: 'blur'}
        ]
      }
    };
  },
  methods: {
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (!valid) {
          return;
        }

        await this.$http.post('/api/table/add', {
          sender: this.addLove.CName,
          sender_sex: this.addLove.CGender,
          recipient: this.addLove.BeCName,
          recipient_sex: this.addLove.BeCGender,
          content: this.addLove.CContent
        }).then((data) => {
          this.$message.success(data.message);
          this.resetForm(formName)
          this.$router.push('./TableList')
        })
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    }
  }
}
</script>

<style scoped>
h3 {
  color: #4a4a4b;
  display: flex;
  justify-content: center;
  margin-bottom: 20px;
  padding-bottom: 20px;
  border-bottom: 1px solid #7c7c7c;
}

.el-form-item__label {
  color: #555;
}
</style>
