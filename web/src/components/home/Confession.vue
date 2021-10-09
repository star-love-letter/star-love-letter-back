<template>
  <div class="confession">
    <el-form :model="addLove" :rules="addLoveRules" ref="addLoveRef" label-width="100px">
      <h3>输入表白信息</h3>
      <el-form-item label="你的名字：" prop="CName">
        <el-input v-model="addLove.CName" placeholder="你的名字"
                  @keyup.enter.native="submitForm('addLoveRef')"></el-input>
      </el-form-item>
      <el-form-item label="你的性别：" prop="CGender">
        <el-select v-model="addLove.CGender" placeholder="请选择性别"
                   @keyup.enter.native="submitForm('addLoveRef')">
          <el-option label="男" value=1></el-option>
          <el-option label="女" value=2></el-option>
          <el-option label="保密" value=0></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="TA的名字：" prop="BeCName">
        <el-input v-model="addLove.BeCName" placeholder="你的名字"
                  @keyup.enter.native="submitForm('addLoveRef')"></el-input>
      </el-form-item>
      <el-form-item label="TA的性别：" prop="BeCGender">
        <el-select v-model="addLove.BeCGender" placeholder="请选择性别"
                   @keyup.enter.native="submitForm('addLoveRef')">
          <el-option label="男" value=1></el-option>
          <el-option label="女" value=2></el-option>
          <el-option label="未知" value=0></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="表白内容：" prop="CContent">
        <el-input type="textarea" :rows="5" v-model="addLove.CContent" placeholder="不得超过160个字" resize="none"></el-input>
      </el-form-item>


      <el-form-item label="上传图片：" v-if="upLoad">
        <el-upload
          ref="upload"
          :action='uploadUrl'
          list-type="picture-card"
          :headers="uploadHeader"
          :on-success="submitUpload"
          :on-remove="handleRemove"
          :on-preview="handlePictureCardPreview"
          :on-exceed="handlePictureExceed"
          multiple="true"
          accept="image/jpeg,image/gif,image/png"
          limit="9">
          <i class="el-icon-plus"></i>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
      </el-form-item>
    </el-form>
    <div class="switch">
      <!--   匿名     -->
      <div class="anonymous">
        <span style="margin-right: 10px">是否匿名</span>
        <el-switch v-model="addLove.anonymous"></el-switch>
      </div>
      <!--   是否邮箱通知     -->
      <div class="notify_email">
        <span style="margin-right: 10px">是否开启邮箱通知</span>
        <el-switch v-model="notifyEmail"></el-switch>
      </div>
    </div>
    <div class="button">
      <el-button type="primary" @click="submitForm('addLoveRef')">立即创建</el-button>
      <el-button @click="resetForm('addLoveRef')">重置</el-button>
    </div>
  </div>
</template>

<script>
import {checkCode, showError} from '../../utils/http';
import axios from "axios";

export default {
  data() {
    return {
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      uploadUrl: axios.defaults.baseURL + '/api/file/image',
      // 上传的头信息
      uploadHeader: {
        token: Vue.prototype.getToken(),
      },
      addLove: {
        CName: '',
        CGender: '',
        BeCName: '',
        BeCGender: '',
        CContent: '',
        anonymous: false,
        notifyEmail: false,
        //上传的图片键值对，方便删除操作
        uploadImageMap: {}
      },
      //显示上传图片按钮
      upLoad: true,
      addLoveRules: {
        CName: [
          {
            required: true,
            message: '请输入你的名字',
            trigger: 'blur'
          },
          {
            max: 6,
            message: '长度不能超过 6 个字符',
            trigger: 'blur'
          }
        ],
        CGender: [
          {
            required: true,
            message: '请选择你的性别',
            trigger: 'change'
          }
        ],
        BeCName: [
          {
            required: true,
            message: '请输入TA的名字',
            trigger: 'change'
          }
        ],
        BeCGender: [
          {
            required: true,
            message: '请选择TA的性别',
            trigger: 'change'
          }
        ],
        CContent: [
          {
            required: true,
            message: '请输入表白内容',
            trigger: 'change'
          },
          {
            max: 160,
            message: '长度不能超过 160 个字符',
            trigger: 'blur'
          }
        ]
      }
    }
  },
  mounted() {
  },
  methods: {
    handlePictureExceed() {
      this.$message.warning("最多只能上传9张图片");
    },
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (!valid) {
          return
        }

        await this.$http.post('/api/table/add', {
          sender: this.addLove.CName,
          senderSex: this.addLove.CGender,
          recipient: this.addLove.BeCName,
          recipientSex: this.addLove.BeCGender,
          content: this.addLove.CContent,
          anonymous: this.addLove.anonymous,
          notifyEmail: this.addLove.notifyEmail,
          images: JSON.stringify(Object.values(this.addLove.uploadImageMap))
        }).then((data) => {
          this.$message.success(data.message)
          this.resetForm(formName)
          this.$router.push('./TableList')
        })
      })
    },
    resetForm(formName) {
      this.$refs[formName].resetFields()
    },
    //移除图片
    handleRemove(file, fileList) {
      delete this.addLove.uploadImageMap[file.uid];
    },
    //放大图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    // 上传时的钩子
    submitUpload(data, file) {
      try {
        //检查状态码
        checkCode(data)
        //添加到图片键值对
        this.addLove.uploadImageMap[file.uid] = data.data;
        console.log(file)
        console.log(this.addLove.uploadImageMap)
      } catch {
        //显示错误信息
        showError(data)
        //移除上传失败的文件
        this.removeFile(file)
      }
    },
    //移除指定文件
    removeFile(file) {
      const length = this.$refs.upload.uploadFiles.length;
      for (let i = 0; i < length; i++) {
        if (this.$refs.upload.uploadFiles[i] === file) {
          this.$refs.upload.uploadFiles.splice(i, 1);
          break;
        }
      }
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
  border-bottom: 1px solid #9e9e9e;
}

.el-form-item__label {
  color: #555;
}

.confession {
  width: 650px;
  height: auto;
  background-color: #fff;
  padding: 20px;
  border-radius: 20px;
}

.switch {
  margin: 20px 0;
  display: flex;
  justify-content: space-around;
}

.button {
  text-align: center;
}

</style>
