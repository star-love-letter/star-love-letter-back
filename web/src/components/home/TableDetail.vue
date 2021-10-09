<template>
  <div class="posts">
    <div class="post" style="height: 20rem">
      <Table :item="TableData" :is-detail="true">
      </Table>
    </div>

    <div id="inputComment" class="input-comment">
      <div style="margin-bottom: 0.7rem;">发布评论</div>
      <div class="text-comment">
        <textarea
          v-model="InputContent"
          placeholder="评论内容"
          name="inputComment"
          rows="8"></textarea>
      </div>
      <button @click="addComment">发布评论</button>
    </div>
    <div class="comment">
      <div class="comment-title">评论(共{{ CommentTotal }}条)</div>
      <ul :data="TableComment">
        <li>
          <div v-for="(item,index) in TableComment" :key="item.id" style="padding-bottom: 10px">
            <a href="/">{{ item.userPublic.name }}：</a>
            <div style="font-size: 0.9rem">{{ item.content }}</div>
            <div style="font-size: 0.75rem">{{ toDates(item.createTime) }}</div>
          </div>
        </li>
      </ul>
    </div>
    <div class="more-comment">暂无更多评论</div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      // 接收从post传过来的单个帖子id
      post_id: this.$route.params.id,
      // 根据帖子id返回的数据
      TableData: '',
      //后端返回的评论内容
      TableComment: {},
      // 页数
      page_index: 1,
      // 一页包含的内容个数
      page_size: 10,
      // 评论总数
      CommentTotal: '',
      // 发布的评论内容
      InputContent: '',
      // 发布的图片列表
      InputImages: [],
      // 当前页面地址
      TableDetailHref: ''
    }
  },
  created() {
    this.getTableData()
    this.getComment()
    this.getCommentTotal()
    // this.getHref()
  },
  methods: {
    // 获取评论
    async getComment() {
      await this.$http.get('/api/comment/pageList', {
        tableId: this.post_id,
        pageIndex: this.page_index,
        pageSize: this.page_size
      }).then((data) => {
        this.TableComment = data.data
      })
    },
    // 获取评论总数
    async getCommentTotal() {
      await this.$http.get('/api/comment/count', {
        tableId: this.post_id
      }).then((data) => {
        this.CommentTotal = data.data
      })
    },

    //发布评论
    async addComment() {
      let name = this.CommentName
      let content = this.InputContent
      if (name === '' || content === '') {
        this.$message.error('请输入名字或者评论内容')
        return
      }

      await this.$http.post('/api/comment/add', {
        tableId: this.TableData.id,
        content: content,
        images:JSON.stringify(this.InputImages)
      }).then((data) => {
        this.$message.success('发布成功')
      })
    },

    // 根据id获取帖子数据
    async getTableData() {
      await this.$http.get('/api/table/table', {
        id: this.post_id
      }).then((data) => {
        this.TableData = data.data
      })
    }
  }
}
</script>
<style src="../../assets/css/posts.css"></style>
<style scoped>
.comment {
  padding: 0 1.25rem 0 1.25rem;
  background-color: #fff;
}

/* 评论标题样式 */
.comment-title {
  padding-bottom: 1rem;
  border-bottom: 0.05rem solid black;
  display: flex;
  justify-content: center;
  background-color: #fff;
}

.comment ul li > div {
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 3rem;
  border-bottom: 0.05rem solid #999;
}

.comment ul li > div:last-child {
  margin-bottom: 0;
}

.comment ul li > div a {
  text-decoration: none;
  font-weight: bold;
  color: #3B6FA8;
}

.more-comment {
  background-color: #fff;
  border-radius: 0 0 0.6rem 0.6rem;
  display: flex;
  justify-content: center;
  padding-top: 1.25rem;
  padding-bottom: 1.25rem;
  color: #828282;
  font-size: 0.75rem;
  font-weight: bold;
}

/* 输入框的样式 */
/*.inputCommentAll{*/
/*  background-color: #fff;*/
/*}*/
.input-comment {
  width: 350px;
  margin-top:10px;
  background-color: #fff;
  text-align: center;
  padding: 1.25rem;
  border-radius: 0.7rem 0.7rem 0 0;
}

.input-comment textarea {
  height: 6.25rem;
  /* 规定能否拉伸样式 */
  resize: none;
  border: 0.05rem solid #999;
  border-radius: 0.5rem;
  margin: 0.6rem;
  padding: 0.8rem;
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

.text-comment {
}
</style>
