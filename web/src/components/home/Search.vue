<template>
  <div class="posts">
    <ul :data="posts">
      <li>
        <div class="post" v-for="(item,index) in posts" :key="item.id">
          <Table :item="item" :is-detail="false">
            <el-button-group>
              <!--   点赞   -->
              <el-button
                icon="fas fa-thumbs-up"
                @click="like(item.sender,item.sender_sex,item.recipient,item.recipient_sex,item.content,item.id)">
                {{ item.thumbs_up }}
              </el-button>
              <!--  评论  -->
              <el-button icon="fas fa-comment-alt" @click="goTableDetail(item.id)"></el-button>
              <el-button icon="fas fa-share-square"></el-button>
            </el-button-group>
          </Table>
        </div>
      </li>
    </ul>
    <div class="block">
      <el-pagination
        :page-size=page_size
        :pager-count="5"
        @current-change=TableListChange
        :current-page=page_index
        :layout="page_layout"
        :total=page_total>
      </el-pagination>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //是否为手机端
      isMobile: null,
      keyword: this.$route.params.keyword,
      posts: {},
      // 当前页
      page_index: 1,
      // 每个页面的内容数量
      page_size: 4,
      // 查询帖子数量
      page_total: 0,
      // 分页样式
      page_layout: ''
    }
  },
  created() {
    //获取当前是否为手机端
    this.isMobile = this.getIsMobile()

    this.search()
  },
  watch: {
    isMobile(newValue, oldValue) {
      //根据分辨率设置分页样式
      if (newValue) {
        this.page_layout = 'prev, pager, next'
      } else {
        this.page_layout = 'prev, pager, next, jumper'
      }
    }
  },
  methods: {
    //打开详情页
    goTableDetail(id) {
      this.$router.push({
        name: 'TableDetail',
        params: {id: id}
      })
    },
    // 获取搜索数据
    async search() {
      await this.$http.get('/api/table/searchList', {
        keyword: this.keyword,
        pageIndex: this.page_index,
        pageSize: this.page_size
      }).then((data) => {
        this.posts = data.data
        this.$message.success(data.message)
        this.getSearchTotal()
      })
    },
    // 页面改变时触发
    TableListChange(newpage) {
      this.page_index = newpage
      this.search()
    },
    // 查询帖子数量
    async getSearchTotal() {
      await this.$http.get('/api/table/searchCount', {
        keyword: this.keyword
      }).then((data) => {
        this.page_total = data.data
      })
    }
  }
}
</script>

<style src="../../assets/css/posts.css"></style>
<style scoped>

</style>
