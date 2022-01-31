<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('testvariables:list')"
            @click.native.prevent="gettestvariablesList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('testvariables:add')"
            @click.native.prevent="showAddtestvariablesDialog"
          >添加变量</el-button>
        </el-form-item>

        <span v-if="hasPermission('testvariables:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.testvariablesname" @keyup.enter.native="searchBy" placeholder="变量名"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="testvariablesList"
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
      <el-table-column label="变量名" align="center" prop="testvariablesname" width="100"/>
      <el-table-column label="变量描述" align="center" prop="variablesdes" width="100"/>
      <el-table-column label="变量类型" align="center" prop="testvariablestype" width="80"/>
      <el-table-column label="变量值类型" align="center" prop="valuetype" width="100"/>
      <el-table-column label="变量值表示" align="center" prop="variablesexpress" width="100"/>
      <el-table-column label="备注" align="center" prop="memo" width="100"/>
      <el-table-column label="操作人" align="center" prop="creator" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('testvariables:update')  || hasPermission('testvariables:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('testvariables:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatetestvariablesDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('testvariables:delete') && scope.row.id !== id"
            @click.native.prevent="removetestvariables(scope.$index)"
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
        label-width="120px"
        style="width: 500px; margin-left:50px;"
        :model="tmptestvariables"
        ref="tmptestvariables"
      >
        <el-form-item label="变量名" prop="testvariablesname" required>
          <el-input
            maxlength="20"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmptestvariables.testvariablesname"
          />
        </el-form-item>

        <el-form-item label="变量描述" prop="variablesdes" required>
          <el-input
            maxlength="20"
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmptestvariables.variablesdes"
          />
        </el-form-item>


        <el-form-item label="变量类型" prop="testvariablestype" required >
          <el-select v-model="tmptestvariables.testvariablestype" placeholder="变量类型" style="width:100%">
            <el-option label="用例变量" value="用例变量"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="变量值类型" prop="valuetype" required >
          <el-select v-model="tmptestvariables.valuetype" placeholder="变量值类型" style="width:100%">
            <el-option label="Number" value="Number"></el-option>
            <el-option label="String" value="String"></el-option>
            <el-option label="Array" value="Array"></el-option>
            <el-option label="Bool" value="Bool"></el-option>
          </el-select>
        </el-form-item>



        <el-form-item label="变量值表示" prop="variablesexpress" required>
          <el-input
            type="textarea"
            rows="5"
            cols="10"
            maxlength="400"
            placeholder="例如 $.store.book[0].title"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmptestvariables.variablesexpress"
          />
          <div class="right">
            <el-tooltip placement="right-start">
              <div slot="content">1.如果获取变量值的接口返回数据类型是Json则使用JsonPath表达式提取变量值，例如：$.store.book[0].title   在线解析网站：http://www.e123456.com/aaaphp/online/jsonpath/<br/>2.如果获取变量值接口返回是html，xml则使用XPath表达式提取变量值， 例如：//div/h3//text()   在线解析网站： http://www.ab173.com/other/xpath.php</div>
              <el-button>变量值表示语法规则</el-button>
            </el-tooltip>
          </div>
        </el-form-item>

        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmptestvariables.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmptestvariables'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addtestvariables"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatetestvariables"
        >修改</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { search, addtestvariables, updatetestvariables, removetestvariables } from '@/api/testvariables/testvariables'
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
        tmptestvariablesname: '',
        testvariablesList: [], // 变量列表
        listLoading: false, // 数据加载等待动画
        total: 0, // 数据总数
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改变量',
          update: '修改变量',
          add: '添加变量'
        },
        btnLoading: false, // 按钮等待动画
        tmptestvariables: {
          id: '',
          testvariablesname: '',
          variablesdes: '',
          valuetype: '',
          testvariablestype: '',
          variablesexpress: '',
          memo: '',
          creator: ''
        },
        search: {
          page: 1,
          size: 10,
          testvariablesname: null
        }
      }
    },

    created() {
      this.gettestvariablesList()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取变量列表
       */
      gettestvariablesList() {
        this.listLoading = true
        this.search.testvariablesname = this.tmptestvariablesname
        search(this.search).then(response => {
          this.testvariablesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载变量列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.testvariablesList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmptestvariablesname = this.search.testvariablesname
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
        this.gettestvariablesList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.gettestvariablesList()
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
       * 显示添加变量对话框
       */
      showAddtestvariablesDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmptestvariables.id = ''
        this.tmptestvariables.testvariablesname = ''
        this.tmptestvariables.variablesdes = ''
        this.tmptestvariables.testvariablestype = ''
        this.tmptestvariables.variablesexpress = ''
        this.tmptestvariables.memo = ''
        this.tmptestvariables.valuetype = ''
        this.tmptestvariables.tmptestvariables = ''
        this.tmptestvariables.creator = this.name
      },
      /**
       * 添加变量
       */
      addtestvariables() {
        this.$refs.tmptestvariables.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addtestvariables(this.tmptestvariables).then(() => {
              this.$message.success('添加成功')
              this.gettestvariablesList()
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
       * 显示修改变量对话框
       * @param index 变量下标
       */
      showUpdatetestvariablesDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmptestvariables.id = this.testvariablesList[index].id
        this.tmptestvariables.testvariablesname = this.testvariablesList[index].testvariablesname
        this.tmptestvariables.variablesdes = this.testvariablesList[index].variablesdes
        this.tmptestvariables.testvariablestype = this.testvariablesList[index].testvariablestype
        this.tmptestvariables.variablesexpress = this.testvariablesList[index].variablesexpress
        this.tmptestvariables.tmptestvariables = this.testvariablesList[index].tmptestvariables
        this.tmptestvariables.valuetype = this.testvariablesList[index].valuetype
        this.tmptestvariables.memo = this.testvariablesList[index].memo
        this.tmptestvariables.creator = this.name
      },
      /**
       * 更新变量
       */
      updatetestvariables() {
        this.$refs.tmptestvariables.validate(valid => {
          if (valid) {
            updatetestvariables(this.tmptestvariables).then(() => {
              this.$message.success('更新成功')
              this.gettestvariablesList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除变量
       * @param index 变量下标
       */
      removetestvariables(index) {
        this.$confirm('删除该变量？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.testvariablesList[index].id
          removetestvariables(id).then(() => {
            this.$message.success('删除成功')
            this.gettestvariablesList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 变量是否唯一
       * @param 变量
       */
      isUniqueDetail(testvariables) {
        for (let i = 0; i < this.testvariablesList.length; i++) {
          if (this.testvariablesList[i].id !== testvariables.id) { // 排除自己
            if (this.testvariablesList[i].testvariablesname === testvariables.testvariablesname) {
              this.$message.error('变量名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
