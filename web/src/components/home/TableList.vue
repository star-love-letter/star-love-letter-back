<template>
  <div class="posts">
    <ul :data="posts">
      <li>
        <div class="post" v-for="(item,index) in posts" :key="item.id">
          <Table :item="item" :is-detail="false">
          </Table>
        </div>
      </li>
    </ul>
    <div class="block">
      <el-pagination
        :page-size="page_size"
        :pager-count="5"
        @current-change=TableListChange
        :current-page="page_index"
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
      type: 1,
      // 当前页
      page_index: 1,
      // 每个页面的内容数量
      page_size: 10,
      // 总条目数
      page_total: 0,
      // 分页样式
      page_layout: '',
      // 后端数据存放在这里
      posts: {},
      genders: '',
      LoadingStatus: false
    }
  },
  created() {
    //获取当前是否为手机端
    this.isMobile = this.getIsMobile()

    this.getTableList()
    this.getTableTotal()
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
    async getTableList() {
      await this.$http.get('/api/table/pageList', {
        pageIndex: this.page_index,
        pageSize: this.page_size
      }).then((data) => {
        this.posts = data.data
      })
    },

    // 获取帖子总数量
    async getTableTotal() {
      await this.$http.get('/api/table/count').then((data) => {
        this.page_total = data.data
      })
    },
    // 页面改变时触发
    TableListChange(newpage) {
      this.page_index = newpage
      this.getTableList()
    }
  }
}
</script>

<style src="../../assets/css/posts.css"></style>
<style scoped>
</style>
