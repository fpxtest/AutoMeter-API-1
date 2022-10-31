<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apiperformancereport:list')"
            @click.native.prevent="getapiperformancereportList"
          >刷新</el-button>
        </el-form-item>

        <span v-if="hasPermission('apiperformancereport:search')">
          <el-form-item label="测试集合" prop="testplanname" >
          <el-select v-model="search.testplanname" placeholder="测试集合" @change="testplanselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testplan, index) in execplanList" :key="index">
              <el-option :label="testplan.executeplanname" :value="testplan.executeplanname" />
            </div>
          </el-select>
        </el-form-item>
          <el-form-item label="执行计划" prop="batchname" >
            <el-select v-model="search.batchname" placeholder="执行计划">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(planbatch, index) in planbatchList" :key="index">
              <el-option :label="planbatch.batchname" :value="planbatch.batchname" />
            </div>
          </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="apiperformancereportList"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="编号" align="center" width="50">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column label="执行计划" align="center" prop="batchname" width="80"/>
      <el-table-column label="用例名" align="center" prop="casename" width="120"/>
      <el-table-column label="API" align="center" prop="apiname" width="80"/>
      <el-table-column label="请求方式" align="center" prop="requestmethod" width="80"/>
      <el-table-column label="状态" align="center" prop="status" width="50">
      <template slot-scope="scope">
        <span v-if="scope.row.status === '失败'" style="color:red">{{ scope.row.status }}</span>
        <span v-else style="color: #37B328">{{ scope.row.status }}</span>
      </template>
      </el-table-column>
      <el-table-column label="微服务" align="center" prop="deployunitname" width="100"/>
      <el-table-column label="请求地址" align="center" prop="url" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.url }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="请求头" align="center" prop="requestheader" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.requestheader }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="请求数据" align="center" prop="requestdatas" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.requestdatas }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="响应" align="center" prop="respone" width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.respone }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>

      <el-table-column label="断言" align="center" prop="expect" width="100">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.expect }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>

      <el-table-column label="断言结果" align="center" prop="assertvalue" width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.assertvalue }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>

      <el-table-column label="运行时间(ms)" align="center" prop="runtime" width="100"/>
      <el-table-column label="异常信息" align="center" prop="errorinfo" width="100">
      <template slot-scope="scope">
        <el-popover trigger="hover" placement="top">
          <p>{{ scope.row.errorinfo }}</p>
          <div slot="reference" class="name-wrapper">
            <el-tag size="medium">...</el-tag>
          </div>
        </el-popover>
      </template>
      </el-table-column>

      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="listQuery.page"
      :page-size="listQuery.size"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        :model="tmpapiperformancereport"
        ref="tmpapiperformancereport"
      >
        <el-form-item label="apiperformancereport名" prop="apiperformancereportname" required>
          <el-input
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapiperformancereport.apiperformancereportname"
          />
        </el-form-item>
        <el-form-item label="访问方式" prop="visittype" required>
          <el-select v-model="tmpapiperformancereport.visittype" placeholder="访问方式">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(vistype, index) in visittypeList" :key="index">
              <el-option :label="vistype.dicitmevalue" :value="vistype.dicitmevalue" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="资源路径" prop="path" required>
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapiperformancereport.path"
          />
        </el-form-item>
        <el-form-item label="微服务" prop="deployunitname" required >
          <el-select v-model="tmpapiperformancereport.deployunitname" placeholder="微服务" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapiperformancereport.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapiperformancereport'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapiperformancereport"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapiperformancereport"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<style>
  .el-table.cell.el-tooltip {
    white-space: pre-wrap;
  }
</style>
<script>
  import { getapiperformancereportList as getapiperformancereportList, search, addapiperformancereport, updateapiperformancereport, removeapiperformancereport } from '@/api/reportcenter/apiperformancereport'
  import { getdepunitList as getdepunitList } from '@/api/deployunit/depunit'
  import { getallexplanbytype as getallexplanbytype } from '@/api/executecenter/executeplan'
  import { getbatchbyplan as getbatchbyplan } from '@/api/executecenter/executeplanbatch'
  import { unix2CurrentTime } from '@/utils'

  export default {
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: 'success',
          draft: 'gray',
          deleted: 'danger'
        }
        return statusMap[status]
      }
    },
    data() {
      return {
        tmptestplanname: '',
        tmptestplanid: null,
        tmpbatchname: null,
        apiperformancereportList: [], // api报告列表
        apiList: [], // api列表
        planbatchList: [], // 执行计划列表
        execplanList: [], // 计划列表
        deployunitList: [], // 微服务列表
        listLoading: false, // 数据加载等待动画
        dicvisitypeQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'httpvisittype' // 获取字典表入参
        },
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          testplanname: '',
          testplanid: null,
          batchname: null
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改apiperformancereport',
          update: '修改apiperformancereport',
          add: '添加apiperformancereport'
        },
        btnLoading: false, // 按钮等待动画
        tmpapiperformancereport: {
          executeplanid: '', // 计划id
          id: '',
          deployunitid: '',
          deployunitname: '',
          batchname: '',
          requestheader: '',
          requestdatas: '',
          apiperformancereportname: '',
          visittype: '',
          path: '',
          url: '',
          requestmethod: '',
          memo: ''
        },
        tmpexecplantype: {
          usetype: ''
        },
        search: {
          page: 1,
          size: 10,
          testplanname: '',
          testplanid: null,
          batchname: null
        }
      }
    },

    created() {
      this.getexecplanList()
      this.getdepunitList()
      this.getapiperformancereportList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      testplanselectChanged(e) {
        for (let i = 0; i < this.execplanList.length; i++) {
          if (this.execplanList[i].executeplanname === e) {
            this.tmpapiperformancereport.executeplanid = this.execplanList[i].id
          }
        }
        getbatchbyplan(this.tmpapiperformancereport).then(response => {
          this.planbatchList = response.data
        }).catch(res => {
          this.$message.error('加载执行计划列表失败')
        })
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      selectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapiperformancereport.deployunitid = this.deployunitList[i].id
          }
          console.log(this.deployunitList[i].id)
        }
      },

      /**
       * 获取api报告列表
       */
      getapiperformancereportList() {
        this.listLoading = true
        getapiperformancereportList(this.listQuery).then(response => {
          this.apiperformancereportList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载api报告列表失败')
        })
      },

      /**
       * 获取测试集合列表
       */
      getexecplanList() {
        this.tmpexecplantype.usetype = '性能'
        getallexplanbytype(this.tmpexecplantype).then(response => {
          this.execplanList = response.data
        }).catch(res => {
          this.$message.error('加载计划列表失败')
        })
      },

      /**
       * 获取微服务列表
       */
      getdepunitList() {
        getdepunitList(this.listQuery).then(response => {
          this.deployunitList = response.data.list
        }).catch(res => {
          this.$message.error('加载微服务列表失败')
        })
      },

      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        this.search.page = this.listQuery.page
        this.search.size = this.listQuery.size
        this.search.testplanid = this.tmpapiperformancereport.executeplanid
        search(this.search).then(response => {
          this.apiperformancereportList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmptestplanname = this.search.testplanname
        this.tmptestplanid = this.search.testplanid
        this.tmpbatchname = this.search.batchname
        this.listLoading = false
        this.btnLoading = false
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.testplanname = this.tmptestplanname
        this.listQuery.testplanid = this.tmptestplanid
        this.listQuery.batchname = this.tmpbatchname
        search(this.listQuery).then(response => {
          this.apiperformancereportList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.listLoading = false
        this.btnLoading = false
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.listQuery.size = size
        this.listQuery.page = 1
        this.searchBypageing()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.listQuery.page = page
        this.searchBypageing()
      },
      /**
       * 表格序号
       * 可参考自定义表格序号
       * http://element-cn.eleme.io/#/zh-CN/component/table#zi-ding-yi-suo-yin
       * @param index 数据下标
       * @returns 表格序号
       */
      getIndex(index) {
        return (this.listQuery.page - 1) * this.listQuery.size + index + 1
      },
      /**
       * 显示添加apiperformancereport对话框
       */
      showAddapiperformancereportDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapiperformancereport.id = ''
        this.tmpapiperformancereport.deployunitid = ''
        this.tmpapiperformancereport.deployunitname = ''
        this.tmpapiperformancereport.apiperformancereportname = ''
        this.tmpapiperformancereport.visittype = ''
        this.tmpapiperformancereport.path = ''
        this.tmpapiperformancereport.memo = ''
      },
      /**
       * 添加apiperformancereport
       */
      addapiperformancereport() {
        this.$refs.tmpapiperformancereport.validate(valid => {
          if (valid && this.isUniqueDetail(this.tmpapiperformancereport)) {
            this.btnLoading = true
            addapiperformancereport(this.tmpapiperformancereport).then(() => {
              this.$message.success('添加成功')
              this.getapiperformancereportList()
              this.dialogFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },
      /**
       * 显示修改apiperformancereport对话框
       * @param index apiperformancereport下标
       */
      showUpdateapiperformancereportDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapiperformancereport.id = this.apiperformancereportList[index].id
        this.tmpapiperformancereport.deployunitid = this.apiperformancereportList[index].deployunitid
        this.tmpapiperformancereport.deployunitname = this.apiperformancereportList[index].deployunitname
        this.tmpapiperformancereport.apiperformancereportname = this.apiperformancereportList[index].apiperformancereportname
        this.tmpapiperformancereport.visittype = this.apiperformancereportList[index].visittype
        this.tmpapiperformancereport.path = this.apiperformancereportList[index].path
        this.tmpapiperformancereport.memo = this.apiperformancereportList[index].memo
      },
      /**
       * 更新apiperformancereport
       */
      updateapiperformancereport() {
        if (this.isUniqueDetail(this.tmpapiperformancereport)) {
          updateapiperformancereport(this.tmpapiperformancereport).then(() => {
            this.$message.success('更新成功')
            this.getapiperformancereportList()
            this.dialogFormVisible = false
          }).catch(res => {
            this.$message.error('更新失败')
          })
        }
      },

      /**
       * 删除字典
       * @param index apiperformancereport下标
       */
      removeapiperformancereport(index) {
        this.$confirm('删除该apiperformancereport？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apiperformancereportList[index].id
          removeapiperformancereport(id).then(() => {
            this.$message.success('删除成功')
            this.getapiperformancereportList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * apiperformancereport资料是否唯一
       * @param apiperformancereport
       */
      isUniqueDetail(apiperformancereport) {
        console.log(apiperformancereport.id)
        for (let i = 0; i < this.apiperformancereportList.length; i++) {
          if (this.apiperformancereportList[i].id !== apiperformancereport.id) { // 排除自己
            console.log(this.apiperformancereportList[i].id)
            if (this.apiperformancereportList[i].apiperformancereportname === apiperformancereport.apiperformancereportname) {
              this.$message.error('apiperformancereport名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
