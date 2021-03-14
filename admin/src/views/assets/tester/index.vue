<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('tester:list')"
            @click.native.prevent="gettesterList"
          >刷新
          </el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('tester:add')"
            @click.native.prevent="showAddtesterDialog"
          >添加测试人员
          </el-button>
        </el-form-item>

        <span v-if="hasPermission('tester:search')">
          <el-form-item>
            <el-input v-model="search.testername" @keyup.enter.native="searchBy" placeholder="测试人员名"></el-input>
          </el-form-item>

          <el-form-item>
            <el-select v-model="search.testertitle" placeholder="职位">
              <el-option label="请选择" value />
              <div v-for="(testertitle, index) in testertitleList" :key="index">
                <el-option :label="testertitle.dicitemname" :value="testertitle.dicitemname"/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="search.testerorg" placeholder="组织">
              <el-option label="请选择" value />
              <div v-for="(testerorg, index) in testerorgList" :key="index">
                <el-option :label="testerorg.dicitemname" :value="testerorg.dicitemname"/>
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
      :data="testerList"
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
      <el-table-column label="测试人员名" align="center" prop="testername" width="120"/>
      <el-table-column label="职务" align="center" prop="testertitle" width="100"/>
      <el-table-column label="所属组织" align="center" prop="testerorg" width="100"/>
      <el-table-column label="备注" align="center" prop="testermemo" width="100"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="160">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center"
                       v-if="hasPermission('tester:update')  || hasPermission('tester:delete')">
        <template slot-scope="scope">
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('tester:update') && scope.row.id !== id"
            @click.native.prevent="showUpdatetesterDialog(scope.$index)"
          >修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('tester:delete') && scope.row.id !== id"
            @click.native.prevent="removetester(scope.$index)"
          >删除
          </el-button>
        </template>
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
        :model="tmptester"
        ref="tmptester"
      >
        <el-form-item label="测试人员名" prop="testername" required>
          <el-input
            type="text"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmptester.testername"
          />
        </el-form-item>

        <el-form-item label="职位" prop="testertitle" required>
          <el-select v-model="tmptester.testertitle" placeholder="职位">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testertitle, index) in testertitleList" :key="index">
              <el-option :label="testertitle.dicitemname" :value="testertitle.dicitemname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="组织" prop="testerorg" required>
          <el-select v-model="tmptester.testerorg" placeholder="组织">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(testerorg, index) in testerorgList" :key="index">
              <el-option :label="testerorg.dicitemname" :value="testerorg.dicitemname" required/>
            </div>
          </el-select>
        </el-form-item>

        <el-form-item label="备注" prop="testermemo" required>
          <el-input
            type="text"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmptester.testermemo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmptester'].resetFields()"
        >重置
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addtester"
        >添加
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updatetester"
        >修改
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { gettesterList as gettesterList, search, addtester, updatetester, removetester } from '@/api/assets/tester'
  import { getdatabydiccodeList as getdatabydiccodeList } from '@/api/system/dictionary'
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
        tmptestername: null,
        tmptestertitle: null,
        tmptesterorg: null,
        testerList: [], // 测试人员列表
        testertitleList: [], // 测试人员职务列表
        testerorgList: [], // 测试人员职务列表
        listLoading: false, // 数据加载等待动画
        diclevelQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'testerlevel' // 获取字典表入参
        },
        dicorgQuery: {
          page: 1, // 页码
          size: 30, // 每页数量
          diccode: 'testerorg' // 获取字典表入参
        },
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          testername: null,
          testertitle: null,
          testerorg: null
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        textMap: {
          updateRole: '修改测试人员',
          update: '修改测试人员',
          add: '添加测试人员'
        },
        btnLoading: false, // 按钮等待动画
        tmptester: {
          id: '',
          testername: '',
          testertitle: '',
          testerorg: '',
          testermemo: ''
        },
        search: {
          page: null,
          size: null,
          testername: null,
          testertitle: null,
          testerorg: null
        }
      }
    },

    created() {
      this.gettesterList()
      this.gettestertitleList()
      this.gettesterorgList()
    },

    methods: {
      unix2CurrentTime,

      /**
       * 获取字典表编码为testlevel的数据
       */
      gettestertitleList() {
        getdatabydiccodeList(this.diclevelQuery).then(response => {
          this.testertitleList = response.data.list
        }).catch(res => {
          this.$message.error('加载角色职务失败')
        })
      },

      /**
       * 获取字典表编码为testorg的数据
       */
      gettesterorgList() {
        getdatabydiccodeList(this.dicorgQuery).then(response => {
          this.testerorgList = response.data.list
          console.log(this.testerorgList)
        }).catch(res => {
          this.$message.error('加载角色组织失败')
        })
      },

      /**
       * 获取字典列表
       */
      gettesterList() {
        this.listLoading = true
        gettesterList(this.listQuery).then(response => {
          this.testerList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载测试人员列表失败')
        })
      },
      searchBy() {
        this.btnLoading = true
        this.listLoading = true
        this.search.page = this.listQuery.page
        this.search.size = this.listQuery.size
        search(this.search).then(response => {
          this.testerList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmptestername = this.search.testername
        this.tmptestertitle = this.search.testertitle
        this.tmptesterorg = this.search.testerorg
        this.listLoading = false
        this.btnLoading = false
      },

      searchBypageing() {
        this.btnLoading = true
        this.listLoading = true
        this.listQuery.testername = this.tmptestername
        this.listQuery.testertitle = this.tmptestertitle
        this.listQuery.testerorg = this.tmptesterorg
        search(this.listQuery).then(response => {
          this.testerList = response.data.list
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
       * 显示添加测试人员对话框
       */
      showAddtesterDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmptester.id = ''
        this.tmptester.testername = ''
        this.tmptester.testertitle = ''
        this.tmptester.testerorg = ''
        this.tmptester.testermemo = ''
      },
      /**
       * 添加测试人员
       */
      addtester() {
        this.$refs.tmptester.validate(valid => {
          if (valid && this.isUniqueDetail(this.tmptester)) {
            this.btnLoading = true
            addtester(this.tmptester).then(() => {
              this.$message.success('添加成功')
              this.gettesterList()
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
       * 显示修改测试人员对话框
       * @param index 测试人员下标
       */
      showUpdatetesterDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmptester.id = this.testerList[index].id
        this.tmptester.testername = this.testerList[index].testername
        this.tmptester.testertitle = this.testerList[index].testertitle
        this.tmptester.testerorg = this.testerList[index].testerorg
        this.tmptester.testermemo = this.testerList[index].testermemo
      },
      /**
       * 更新测试人员
       */
      updatetester() {
        this.$refs.tmptester.validate(valid => {
          if (valid && this.isUniqueDetail(this.tmptester)) {
            updatetester(this.tmptester).then(() => {
              this.$message.success('更新成功')
              this.gettesterList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除测试人员
       * @param index 测试人员下标
       */
      removetester(index) {
        this.$confirm('删除该测试人员？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.testerList[index].id
          removetester(id).then(() => {
            this.$message.success('删除成功')
            this.gettesterList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 测试人员是否唯一
       * @param 测试人员
       */
      isUniqueDetail(tester) {
        for (let i = 0; i < this.testerList.length; i++) {
          if (this.testerList[i].id !== tester.id) { // 排除自己
            if (this.testerList[i].testername === tester.testername) {
              this.$message.error('测试人员名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
