<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('dbcondition:list')"
            @click.native.prevent="getdbconditionList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('dbcondition:add')"
            @click.native.prevent="showAdddbconditionDialog"
          >添加DB条件</el-button>
        </el-form-item>

        <span v-if="hasPermission('dbcondition:search')">
          <el-form-item>
            <el-input clearable v-model="search.dbtype" @keyup.enter.native="searchBy" placeholder="DB类型"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="dbconditionList"
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
      <el-table-column label="数据库类型" align="center" prop="dbtype" width="120"/>
      <el-table-column label="数据库内容" align="center" prop="dbcontent" width="120"/>
      <el-table-column label="连接字" align="center" prop="connectstr" width="120"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('dbcondition:update')  || hasPermission('dbcondition:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('dbcondition:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatedbconditionDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('dbcondition:delete') && scope.row.id !== id"
            @click.native.prevent="removedbcondition(scope.$index)"
          >删除</el-button>
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
        label-width="100px"
        style="width: 300px; margin-left:50px;"
        :model="tmpdbcondition"
        ref="tmpdbcondition"
      >
        <el-form-item label="测试环境" prop="enviromentname" required >
          <el-select v-model="tmpdbcondition.enviromentname"  placeholder="测试环境名" @change="selectChangedEN($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(envname, index) in enviromentnameList" :key="index">
              <el-option :label="envname.enviromentname" :value="envname.enviromentname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="服务器" prop="machinename" required >
          <el-select v-model="tmpdbcondition.machinename" placeholder="服务器" @change="selectChangedMN($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(macname, index) in machinenameList" :key="index">
              <el-option :label="`${macname.machinename} ：${macname.ip}`" :value="macname.machinename" required/>
            </div>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpdbcondition'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="adddbcondition"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatedbcondition"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, adddbcondition, updatedbcondition, removedbcondition } from '@/api/condition/dbcondition'
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
        tmpenviromentname: '',
        dbconditionList: [], // 环境服务器列表
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
          updateRole: '修改测试环境',
          update: '修改测试环境',
          add: '添加测试环境'
        },
        btnLoading: false, // 按钮等待动画
        tmpdbcondition: {
          id: '',
          conditionid: '',
          enviromentid: '',
          dbtype: '',
          dbcontent: '',
          connectstr: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          dbtype: null
        }
      }
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    created() {
      this.getdbconditionList()
      this.getenviromentallList()
      this.getmachineLists()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChangedEN(e) {
        for (let i = 0; i < this.enviromentnameList.length; i++) {
          if (this.enviromentnameList[i].enviromentname === e) {
            this.tmpdbcondition.envid = this.enviromentnameList[i].id
          }
          console.log(this.enviromentnameList[i].id)
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChangedMN(e) {
        for (let i = 0; i < this.machinenameList.length; i++) {
          if (this.machinenameList[i].machinename === e) {
            this.tmpdbcondition.machineid = this.machinenameList[i].id
          }
          console.log(this.machinenameList[i].id)
        }
      },

      /**
       * 获取服务器环境列表
       */
      getdbconditionList() {
        this.listLoading = true
        this.search.enviromentname = this.tmpenviromentname
        search(this.search).then(response => {
          this.dbconditionList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载测试环境服务器列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.dbconditionList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpenviromentname = this.search.enviromentname
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
        this.getdbconditionList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getdbconditionList()
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
       * 显示添加测试环境对话框
       */
      showAdddbconditionDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpdbcondition.id = ''
        this.tmpdbcondition.envid = ''
        this.tmpdbcondition.machineid = ''
        this.tmpdbcondition.machinename = ''
        this.tmpdbcondition.enviromentname = ''
        this.tmpdbcondition.creator = this.name
      },
      /**
       * 添加测试环境
       */
      adddbcondition() {
        this.$refs.tmpdbcondition.validate(valid => {
          if (valid) {
            this.btnLoading = true
            adddbcondition(this.tmpdbcondition).then(() => {
              this.$message.success('添加成功')
              this.getdbconditionList()
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
       * 显示修改测试环境对话框
       * @param index 测试环境下标
       */
      showUpdatedbconditionDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpdbcondition.id = this.dbconditionList[index].id
        this.tmpdbcondition.envid = this.dbconditionList[index].envid
        this.tmpdbcondition.machineid = this.dbconditionList[index].machineid
        this.tmpdbcondition.machinename = this.dbconditionList[index].machinename
        this.tmpdbcondition.enviromentname = this.dbconditionList[index].enviromentname
        this.tmpdbcondition.creator = this.name
      },
      /**
       * 更新测试环境
       */
      updatedbcondition() {
        this.$refs.tmpdbcondition.validate(valid => {
          if (valid) {
            updatedbcondition(this.tmpdbcondition).then(() => {
              this.$message.success('更新成功')
              this.getdbconditionList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除测试环境
       * @param index 测试环境下标
       */
      removedbcondition(index) {
        this.$confirm('删除该测试环境？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.dbconditionList[index].id
          removedbcondition(id).then(() => {
            this.$message.success('删除成功')
            this.getdbconditionList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 测试环境是否唯一
       * @param 测试环境
       */
      isUniqueDetail(dbcondition) {
        for (let i = 0; i < this.dbconditionList.length; i++) {
          if (this.dbconditionList[i].id !== dbcondition.id) { // 排除自己
            if (this.dbconditionList[i].enviromentname === dbcondition.enviromentname) {
              if (this.dbconditionList[i].machinename === dbcondition.machinename) {
                this.$message.error('测试环境名已存在')
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
