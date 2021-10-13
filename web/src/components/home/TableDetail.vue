<template>
  <div style="width: 100%; margin: 0px auto">
    <div class="container">
      <div>
        <Table :item="TableData" :is-detail="true"> </Table>
      </div>
      <div style="border: 1px solid rgb(193 193 193)">
        <div id="inputComment" class="input-comment" v-if="inputCommentShow">
          <el-form
            :model="inputComment"
            :rules="inputCommentRules"
            ref="inputCommentRef"
            label-width="100px"
          >
            <h3 style="margin-bottom: 14px">发布评论</h3>
            <el-form-item label="评论内容：" prop="InputContent">
              <el-input
                type="textarea"
                :rows="4"
                placeholder="请输入内容"
                resize="none"
                v-model="inputComment.InputContent"
              >
              </el-input>
            </el-form-item>
            <el-form-item label="评论内容：" style="text-align: left">
              <el-upload
                ref="upload"
                :action="uploadUrl"
                list-type="picture-card"
                :headers="uploadHeader"
                :on-success="submitUpload"
                :on-remove="handleRemove"
                :on-preview="handlePictureCardPreview"
                :on-exceed="handlePictureExceed"
                multiple="true"
                accept="image/jpeg,image/gif,image/png"
                limit="6"
              >
                <i class="el-icon-plus"></i>
              </el-upload>
              <el-dialog :visible.sync="dialogVisible">
                <img width="100%" :src="dialogImageUrl" alt="" />
              </el-dialog>
            </el-form-item>
            <button @click="addComment('inputCommentRef')">发布评论</button>
          </el-form>
        </div>
        <div class="comment-container">
          <div class="comment-list">
            <div class="comment-title">
              <span> 评论(共{{ CommentTotal }}条) </span>
              <el-button
                class="input-comment-button"
                size="small"
                @click="inputCommentShow = !inputCommentShow"
                type="primary"
                plain
                >发布评论</el-button
              >
            </div>
            <Comment v-for="item in TableComment" :key="item.id" :item="item">
            </Comment>
          </div>
          <div class="more-comment">暂无更多评论</div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { checkCode, showError } from "../../utils/http";
import axios from "axios";
import Comment from "../module/Comment.vue";
export default {
  components: { Comment },
  data() {
    return {
      // 接收从post传过来的单个帖子id
      post_id: this.$route.params.id,
      // 根据帖子id返回的数据
      TableData: "",
      //后端返回的评论内容
      TableComment: {},
      // 页数
      page_index: 1,
      // 一页包含的内容个数
      page_size: 10,
      // 评论总数
      CommentTotal: "",
      // 评论内容的数据
      inputComment: {
        // 发布的评论内容
        InputContent: "",
        //上传的图片键值对，方便删除操作
        uploadImageMap: {},
      },
      uploadUrl: axios.defaults.baseURL + "/api/file/image",
      // 上传的头信息
      uploadHeader: {
        token: Vue.prototype.getToken(),
      },
      // 当前页面地址
      TableDetailHref: "",
      dialogVisible: false,
      // 输入评论的验证规则
      inputCommentRules: {
        InputContent: [
          {
            required: true,
            message: "请输入评论内容",
            trigger: "blur",
          },
        ],
      },
      inputCommentShow: false,
    };
  },
  created() {
    this.getTableData();
    this.getComment();
    this.getCommentTotal();
  },
  methods: {
    // 获取评论
    async getComment() {
      await this.$http
        .get("/api/comment/pageList", {
          tableId: this.post_id,
          pageIndex: this.page_index,
          pageSize: this.page_size,
        })
        .then((data) => {
          console.log(data);
          this.TableComment = data.data;
        });
    },
    // 获取评论总数
    async getCommentTotal() {
      await this.$http
        .get("/api/comment/count", {
          tableId: this.post_id,
        })
        .then((data) => {
          this.CommentTotal = data.data;
        });
    },

    //发布评论
    async addComment(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (!valid) {
          return;
        }

        await this.$http
          .post("/api/comment/add", {
            tableId: this.TableData.id,
            content: this.inputComment.InputContent,
            images: JSON.stringify(
              Object.values(this.inputComment.uploadImageMap)
            ),
          })
          .then((data) => {
            console.log(data);
            this.getComment();
            this.$message.success("发布成功");
          });
      });
    },

    // 根据id获取帖子数据
    async getTableData() {
      await this.$http
        .get("/api/table/table", {
          id: this.post_id,
        })
        .then((data) => {
          this.TableData = data.data;
        });
    },
    //移除图片
    handleRemove(file, fileList) {
      delete this.inputComment.uploadImageMap[file.uid];
    },
    //放大图片
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // 上传时的钩子
    submitUpload(data, file) {
      try {
        //检查状态码
        checkCode(data);
        //添加到图片键值对
        this.inputComment.uploadImageMap[file.uid] = data.data;
        console.log(file);
        console.log(this.inputComment.uploadImageMap);
      } catch {
        //显示错误信息
        showError(data);
        //移除上传失败的文件
        this.removeFile(file);
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
    },
    handlePictureExceed() {
      this.$message.warning("最多只能上传9张图片");
    },
  },
};
</script>
<style src="../../assets/css/posts.css"></style>
<style scoped>
/* 评论标题样式 */
.comment-title {
  text-align: center;
  background-color: #fff;
  padding-top: 10px;
  font-size: 20px;
  position: relative;
}

.more-comment {
  background-color: #fff;
  display: flex;
  justify-content: center;
  padding-top: 1.25rem;
  padding-bottom: 1.25rem;
  color: #828282;
  font-size: 0.75rem;
  font-weight: bold;
}

.input-comment {
  background-color: #fff;
  text-align: center;
  padding: 1.25rem;
}

.input-comment button {
  background-color: #7482e1;
  color: white;
  border: 0.05rem solid #999;
  border-radius: 0.5rem;
  padding: 0.3rem;
}

.input-comment button:hover {
  background-color: #999;
  cursor: pointer;
}

.container {
  max-width: 726px;
  margin: 0 auto;
}

.input-comment-button {
  position: absolute;
  top: 6px;
  right: 20px;
}
</style>
