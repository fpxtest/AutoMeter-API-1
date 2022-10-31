<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('conditionorder:list')"
            @click.native.prevent="getconditionorderList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('conditionorder:add')"
            @click.native.prevent="showAddconditionorderDialog"
          >添加顺序</el-button>
        </el-form-item>

        <span v-if="hasPermission('conditionorder:search')">
          <el-form-item>
            <el-input clearable v-model="search.conditionname" @keyup.enter.native="searchBy" placeholder="父条件名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-input clearable v-model="search.subconditionname" @keyup.enter.native="searchBy" placeholder="子条件名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="conditionorderList"
      :key="itemKey"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="编号" align="center" width="60">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>
      <el-table-column label="父条件名" align="center" prop="conditionname" width="120"/>
      <el-table-column label="数据库条件名" align="center" prop="subconditionname" width="120"/>
      <el-table-column label="环境" align="center" prop="enviromentname" width="120"/>
      <el-table-column label="组件名" align="center" prop="assemblename" width="80"/>
      <el-table-column label="数据库类型" align="center" prop="dbtype" width="120"/>
      <el-table-column label="数据库内容" align="center" prop="dbcontent" width="120">
        <template slot-scope="scope">
          <el-popover trigger="hover" placement="top">
            <p>{{ scope.row.dbcontent }}</p>
            <div slot="reference" class="name-wrapper">
              <el-tag size="medium">...</el-tag>
            </div>
          </el-popover>
        </template>
      </el-table-column>>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('conditionorder:moveup')  || hasPermission('conditionorder:movedown')">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('conditionorder:moveup') && scope.row.id !== id"
            @click.native.prevent="showUpdateconditionorderDialog(scope.$index)"
          >上移</el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('conditionorder:movedown') && scope.row.id !== id"
            @click.native.prevent="removeconditionorder(scope.$index)"
          >下移</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="search.page"
      :page-size="search.size"
      :total="total"
      :page-sizes="[10, 20, 30, 40]"
      layout="total, sizes, prev, pager, next, jumper"
    ></el-pagination>
    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="120px"
        style="width: 600px; margin-left:30px;"
        :model="tmpconditionorder"
        ref="tmpconditionorder"
      >
        <el-form-item label="父条件：" prop="conditionname" required >
          <el-select v-model="tmpconditionorder.conditionname"  placeholder="父条件名" style="width:100%" @change="ConditionselectChangedPC($event)" >
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(condition, index) in conditionList" :key="index">
              <el-option :label="condition.conditionname" :value="condition.conditionname" required/>
            </div>
          </el-select>
        </el-form-item>


        <el-table
          :data="URLModles"
          :show-header="false"
          highlight-current-row
          style="width: 100%"  @selection-change="handleSelectionChange">
          <el-table-column label="编号" align="center" width="60">
            <template slot-scope="scope">
              <span v-text="getIndex(scope.$index)"></span>
            </template>
          </el-table-column>
          <el-table-column label="父条件名" align="center" prop="conditionname" width="120"/>
          <el-table-column label="数据库条件名" align="center" prop="subconditionname" width="120"/>
          <el-table-column
            prop="expressCode"
            label="快递代码" width="100px">
          </el-table-column>
          <el-table-column
            prop="expressName"
            label="快递名称"  width="100px">
          </el-table-column>
          <el-table-column label="操作" >
            <template slot-scope="scope">
              <el-button
                size="mini"
                :disabled="scope.$index===0"
                @click="moveUp(scope.$index,scope.row)"><i class="el-icon-arrow-up"></i>上移</el-button>
              <el-button
                size="mini"
                :disabled="scope.$index===(URLModles.length-1)"
                @click="moveDown(scope.$index,scope.row)"><i class="el-icon-arrow-down"></i>下移</el-button>
            </template>

          </el-table-column>
        </el-table>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpconditionorder'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addconditionorder"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateconditionorder"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { getenviromentallList as getenviromentallList } from '@/api/enviroment/testenviroment'
  import { getconditionallList as getconditionallList } from '@/api/condition/condition'
  import { getassembleallnameList as getassembleallnameList } from '@/api/enviroment/enviromentassemble'
  import { searchconditionorder, addconditionorder, updateconditionorder, removeconditionorder } from '@/api/condition/conditionorder'
  import { unix2CurrentTime } from '@/utils'
  import { mapGetters } from 'vuex'

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
        itemKey: null,
        tmpconditionname: '',
        tmpsubconditionname: '',
        conditionorderList: [], // 环境服务器列表
        conditionList: [], // 条件服务器列表
        enviroment_assembleList: [], // 环境组件列表
        enviromentnameList: [], // 环境列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          enviromentname: ''
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改数据库条件',
          update: '修改数据库条件',
          add: '添加数据库条件'
        },
        btnLoading: false, // 按钮等待动画
        tmpconditionorder: {
          id: '',
          conditionid: '',
          conditionname: '',
          assembleid: '',
          assemblename: '',
          subconditionname: '',
          enviromentid: '',
          enviromentname: '',
          dbtype: '',
          dbcontent: '',
          connectstr: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          conditionname: null,
          subconditionname: null
        }
      }
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    created() {
      this.getconditionallList()
      this.getassembleallnameList()
      this.getconditionorderList()
      this.getenviromentallList()
      this.getmachineLists()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取环境列表
       */
      getenviromentallList() {
        this.listLoading = true
        getenviromentallList().then(response => {
          this.enviromentnameList = response.data
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载数据库条件列表失败')
        })
      },

      /**
       * 获取父条件列表
       */
      getconditionallList() {
        getconditionallList().then(response => {
          this.conditionList = response.data
        }).catch(res => {
          this.$message.error('获取父条件列表失败')
        })
      },

      /**
       * 获取组件列表
       */
      getassembleallnameList() {
        getassembleallnameList().then(response => {
          this.enviroment_assembleList = response.data
        }).catch(res => {
          this.$message.error('获取组件列表失败')
        })
      },
      /**
       * 环境下拉选择事件获取环境id  e的值为options的选值
       */
      selectChangedEN(e) {
        for (let i = 0; i < this.enviromentnameList.length; i++) {
          if (this.enviromentnameList[i].enviromentname === e) {
            this.tmpconditionorder.enviromentid = this.enviromentnameList[i].id
          }
          console.log(this.enviromentnameList[i].id)
        }
      },

      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      ConditionselectChangedPC(e) {
        for (let i = 0; i < this.conditionList.length; i++) {
          if (this.conditionList[i].conditionname === e) {
            this.tmpconditionorder.conditionid = this.conditionList[i].id
          }
        }
      },

      /**
       * 组件下拉选择事件获取组件id  e的值为options的选值
       */
      ConditionselectChangedAS(e) {
        for (let i = 0; i < this.enviroment_assembleList.length; i++) {
          if (this.enviroment_assembleList[i].assemblename === e) {
            this.tmpconditionorder.assembleid = this.enviroment_assembleList[i].id
          }
        }
      },
      /**
       * 微服务下拉选择事件获取微服务id  e的值为options的选值
       */
      selectChangedMN(e) {
        for (let i = 0; i < this.machinenameList.length; i++) {
          if (this.machinenameList[i].machinename === e) {
            this.tmpconditionorder.machineid = this.machinenameList[i].id
          }
          console.log(this.machinenameList[i].id)
        }
      },

      /**
       * 获取服务器环境列表
       */
      getconditionorderList() {
        this.listLoading = true
        this.search.conditionname = this.tmpconditionname
        this.search.subconditionname = this.tmpsubconditionname
        searchconditionorder(this.search).then(response => {
          this.conditionorderList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载数据库条件服务器列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        searchconditionorder(this.search).then(response => {
          this.itemKey = Math.random()
          this.conditionorderList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpconditionname = this.search.conditionname
        this.tmpsubconditionname = this.search.subconditionname
        this.listLoading = false
        this.btnLoading = false
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.search.page = 1
        this.search.size = size
        this.getconditionorderList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getconditionorderList()
      },
      /**
       * 表格序号
       * 可参考自定义表格序号
       * http://element-cn.eleme.io/#/zh-CN/component/table#zi-ding-yi-suo-yin
       * @param index 数据下标
       * @returns 表格序号
       */
      getIndex(index) {
        return (this.search.page - 1) * this.search.size + index + 1
      },
      /**
       * 显示添加数据库条件对话框
       */
      showAddconditionorderDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpconditionorder.id = ''
        this.tmpconditionorder.conditionname = ''
        this.tmpconditionorder.enviromentid = ''
        this.tmpconditionorder.enviromentname = ''
        this.tmpconditionorder.assembleid = ''
        this.tmpconditionorder.assemblename = ''
        this.tmpconditionorder.subconditionname = ''
        this.tmpconditionorder.dbtype = ''
        this.tmpconditionorder.dbcontent = ''
        this.tmpconditionorder.creator = this.name
      },
      /**
       * 添加数据库条件
       */
      addconditionorder() {
        this.$refs.tmpconditionorder.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addconditionorder(this.tmpconditionorder).then(() => {
              this.$message.success('添加成功')
              this.getconditionorderList()
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
       * 显示修改数据库条件对话框
       * @param index 数据库条件下标
       */
      showUpdateconditionorderDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpconditionorder.id = this.conditionorderList[index].id
        this.tmpconditionorder.conditionid = this.conditionorderList[index].conditionid
        this.tmpconditionorder.assembleid = this.conditionorderList[index].assembleid
        this.tmpconditionorder.enviromentid = this.conditionorderList[index].enviromentid
        this.tmpconditionorder.enviromentname = this.conditionorderList[index].enviromentname
        this.tmpconditionorder.assemblename = this.conditionorderList[index].assemblename
        this.tmpconditionorder.conditionname = this.conditionorderList[index].conditionname
        this.tmpconditionorder.subconditionname = this.conditionorderList[index].subconditionname
        this.tmpconditionorder.dbtype = this.conditionorderList[index].dbtype
        this.tmpconditionorder.dbcontent = this.conditionorderList[index].dbcontent
        this.tmpconditionorder.creator = this.name
      },
      /**
       * 更新数据库条件
       */
      updateconditionorder() {
        this.$refs.tmpconditionorder.validate(valid => {
          if (valid) {
            updateconditionorder(this.tmpconditionorder).then(() => {
              this.$message.success('更新成功')
              this.getconditionorderList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除数据库条件
       * @param index 数据库条件下标
       */
      removeconditionorder(index) {
        this.$confirm('删除该数据库条件？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.conditionorderList[index].id
          removeconditionorder(id).then(() => {
            this.$message.success('删除成功')
            this.getconditionorderList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 数据库条件是否唯一
       * @param 数据库条件
       */
      isUniqueDetail(conditionorder) {
        for (let i = 0; i < this.conditionorderList.length; i++) {
          if (this.conditionorderList[i].id !== conditionorder.id) { // 排除自己
            if (this.conditionorderList[i].enviromentname === conditionorder.enviromentname) {
              if (this.conditionorderList[i].machinename === conditionorder.machinename) {
                this.$message.error('数据库条件名已存在')
                return false
              }
            }
          }
        }
        return true
      }
    }
  }
</script>
