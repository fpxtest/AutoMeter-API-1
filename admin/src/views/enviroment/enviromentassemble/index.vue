<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('enviroment_assemble:list')"
            @click.native.prevent="getenviroment_assembleList"
          >刷新</el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('enviroment_assemble:add')"
            @click.native.prevent="showAddenviroment_assembleDialog"
          >添加环境中间件</el-button>
        </el-form-item>

        <span v-if="hasPermission('enviroment_assemble:search')">
          <el-form-item>
            <el-input clearable maxlength="40" v-model="search.assemblename" @keyup.enter.native="searchBy" placeholder="中间件名"></el-input>
          </el-form-item>
          <el-form-item label="中间件名" prop="assembletype" >
          <el-select v-model="search.assembletype" clearable placeholder="中间件名">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(asstype, index) in assembleypeList" :key="index">
              <el-option :label="asstype.dicitmevalue" :value="asstype.dicitmevalue" />
            </div>
          </el-select>
        </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchBy"  :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="enviroment_assembleList"
      v-loading.body="listLoading"
      :key="itemKey"
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
      <el-table-column label="中间件名" align="center" prop="assemblename" width="100"/>
      <el-table-column label="中间件类型" align="center" prop="assembletype" width="90"/>
        <el-table-column :show-overflow-tooltip="true" label="连接字" align="center" prop="connectstr" width="200"/>
      <el-table-column :show-overflow-tooltip="true" label="备注" align="center" prop="memo" width="150"/>
      <el-table-column label="操作人" align="center" prop="creator" width="80"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="140">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>

      <el-table-column label="管理" align="center" width="240"
                       v-if="hasPermission('enviroment_assemble:update')  || hasPermission('enviroment_assemble:delete')">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('enviroment_assemble:update') && scope.row.id !== id"
            @click.native.prevent="showassembletestDialog(scope.$index)"
          >连接测试</el-button>
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('enviroment_assemble:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateenviroment_assembleDialog(scope.$index)"
          >修改</el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('enviroment_assemble:delete') && scope.row.id !== id"
            @click.native.prevent="removeenviroment_assemble(scope.$index)"
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
        :model="tmpenviroment_assemble"
        ref="tmpenviroment_assemble"
      >
        <el-form-item label="中间件名" prop="assemblename" required>
          <el-input
            type="text"
            maxlength="60"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpenviroment_assemble.assemblename"
          />
        </el-form-item>
        <el-form-item label="中间件类型" prop="assembletype" required >
          <el-select v-model="tmpenviroment_assemble.assembletype" placeholder="中间件类型" style="width:100%" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(asstype, index) in assembleypeList" :key="index">
              <el-option :label="asstype.dicitmevalue" :value="asstype.dicitmevalue" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="连接字" prop="connectstr" required>
          <el-input
            type="textarea"
            maxlength="60"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpenviroment_assemble.connectstr"
            placeholder="账号，密码，端口，库名 用英文逗号分开，例子：root,root,3306,admin"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-input
            type="text"
            maxlength="100"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpenviroment_assemble.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpenviroment_assemble'].resetFields()"
        >重置</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addenviroment_assemble"
        >添加</el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateenviroment_assemble"
        >修改</el-button>
      </div>
    </el-dialog>
    <el-dialog title="测试连接" :visible.sync="testdialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="120px"
        style="width: 500px; margin-left:50px;"
        :model="tmptest"
        ref="tmptest"
      >
        <el-form-item label="连接服务器：" prop="machinename" required >
          <el-select v-model="tmptest.machinename" placeholder="连接服务器" style="width:100%" @change="selectChangedMN($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(macname, index) in machinenameList" :key="index">
              <el-option :label="`${macname.machinename} ：${macname.ip}`" :value="macname.machinename" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="访问方式" prop="visittype" required >
          <el-select v-model="tmptest.visittype" placeholder="访问方式" style="width:100%" @change="selectChangedVisittype($event)">
            <el-option label="IP" value="IP"></el-option>
            <el-option label="域名" value="域名"></el-option>
          </el-select>
        </el-form-item>

        <div v-if="domianVisible">
          <el-form-item label="访问域名" prop="domain" required>
            <el-input v-model="tmptest.domain"  placeholder="访问域名" required></el-input>
          </el-form-item>
        </div>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="testdialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="runtest"
        >测试</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
  import { searchenviroment_assemble as searchenviroment_assemble, addenviroment_assemble, updateenviroment_assemble, removeenviroment_assemble, runtest } from '@/api/enviroment/enviromentassemble'
  import { unix2CurrentTime } from '@/utils'
  import { getdatabydiccodeList as getdatabydiccodeList } from '@/api/system/dictionary'
  import { mapGetters } from 'vuex'
  import { getmachineLists as getmachineLists } from '@/api/assets/machine'

  export default {
    name: '环境中间件',
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
        id: null,
        itemKey: null,
        tmpassemblename: null,
        tmpassembletype: null,
        enviroment_assembleList: [], // 环境列表
        machinenameList: [], // 服务器列表
        assembleypeList: [], // 环境列表
        listLoading: false, // 数据加载等待动画
        domianVisible: false,
        total: 0, // 数据总数
        listQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          listLoading: true,
          assembletype: ''
        },
        dialogStatus: 'add',
        dialogFormVisible: false,
        testdialogFormVisible: false,
        textMap: {
          updateRole: '修改环境中间件',
          update: '修改环境中间件',
          add: '添加环境中间件'
        },
        btnLoading: false, // 按钮等待动画
        diclevelQuery: {
          page: 1, // 页码
          size: 100, // 每页数量
          diccode: 'machinedeploy' // 获取字典表入参
        },
        tmpenviroment_assemble: {
          id: '',
          assemblename: '',
          assembletype: '',
          connectstr: '',
          memo: '',
          creator: '',
          projectid: ''
        },
        tmptest: {
          machineid: '',
          machinename: '',
          visittype: '',
          assembletype: '',
          domain: '',
          constr: ''
        },
        search: {
          page: 1,
          size: 10,
          assembletype: null,
          assemblename: null,
          projectid: ''
        }
      }
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'projectlist', 'projectid'])
    },

    created() {
      this.search.projectid = window.localStorage.getItem('pid')
      this.getenviroment_assembleList()
      this.getdatabydiccodeList()
      this.getmachineLists()
    },

    activated() {
      this.getmachineLists()
    },

    methods: {
      unix2CurrentTime,
      selectChanged(e) {
        this.tmpenviroment_assemble.connectstr = ''
      },
      /**
       * 获取中间件名字典列表
       */
      getdatabydiccodeList() {
        getdatabydiccodeList(this.diclevelQuery).then(response => {
          this.assembleypeList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('加载中间件名字典列表失败')
        })
      },

      /**
       * 获取服务器列表
       */
      getmachineLists() {
        getmachineLists(this.search).then(response => {
          this.machinenameList = response.data
        }).catch(res => {
          this.$message.error('加载服务器列表失败')
        })
      },
      /**
       * 获取中间件列表
       */
      getenviroment_assembleList() {
        this.listLoading = true
        this.search.assembletype = this.tmpassembletype
        this.search.assemblename = this.tmpassemblename
        searchenviroment_assemble(this.search).then(response => {
          this.enviroment_assembleList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载测试环境列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        searchenviroment_assemble(this.search).then(response => {
          this.itemKey = Math.random()
          this.enviroment_assembleList = response.data.list
          this.total = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.listLoading = false
        this.btnLoading = false
        this.tmpassembletype = this.search.assembletype
        this.tmpassemblename = this.search.assemblename
      },

      /**
       * 微服务访问方式下拉控制是否显示域名  e的值为options的选值
       */
      selectChangedVisittype(e) {
        if (e === '域名') {
          this.domianVisible = true
        }
        if (e === 'IP') {
          this.domianVisible = false
        }
      },

      /**
       * 服务器下拉选择事件获取微服务id  e的值为options的选值
       */
      selectChangedMN(e) {
        console.log(e)
        for (let i = 0; i < this.machinenameList.length; i++) {
          if (this.machinenameList[i].machinename === e) {
            this.tmptest.machineid = this.machinenameList[i].id
          }
        }
      },
      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.search.page = 1
        this.search.size = size
        this.getenviroment_assembleList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getenviroment_assembleList()
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
      showAddenviroment_assembleDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpenviroment_assemble.id = ''
        this.tmpenviroment_assemble.assemblename = ''
        this.tmpenviroment_assemble.assembletype = ''
        this.tmpenviroment_assemble.connectstr = ''
        this.tmpenviroment_assemble.memo = ''
        this.tmpenviroment_assemble.creator = this.name
        this.tmpenviroment_assemble.projectid = window.localStorage.getItem('pid')
      },
      /**
       * 添加测试环境
       */
      addenviroment_assemble() {
        this.$refs.tmpenviroment_assemble.validate(valid => {
          if (valid) {
            this.btnLoading = true
            addenviroment_assemble(this.tmpenviroment_assemble).then(() => {
              this.$message.success('添加成功')
              this.getenviroment_assembleList()
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
       * 测试
       */
      runtest() {
        this.$refs.tmptest.validate(valid => {
          if (valid) {
            runtest(this.tmptest).then(() => {
              this.$message.success('连接成功！')
            }).catch(res => {
              this.$message.error('添加失败')
            })
          }
        })
      },
      /**
       * 显示测试对话框
       * @param index 测试环境下标
       */
      showassembletestDialog(index) {
        this.testdialogFormVisible = true
        this.tmptest.machinename = ''
        this.tmptest.visittype = ''
        this.tmptest.domain = ''
        this.domianVisible = false
        this.tmptest.assembletype = this.enviroment_assembleList[index].assembletype
        this.tmptest.constr = this.enviroment_assembleList[index].connectstr
        this.tmpenviroment_assemble.projectid = window.localStorage.getItem('pid')
      },

      /**
       * 显示修改测试环境对话框
       * @param index 测试环境下标
       */
      showUpdateenviroment_assembleDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpenviroment_assemble.id = this.enviroment_assembleList[index].id
        this.tmpenviroment_assemble.assemblename = this.enviroment_assembleList[index].assemblename
        this.tmpenviroment_assemble.assembletype = this.enviroment_assembleList[index].assembletype
        this.tmpenviroment_assemble.connectstr = this.enviroment_assembleList[index].connectstr
        this.tmpenviroment_assemble.memo = this.enviroment_assembleList[index].memo
        this.tmpenviroment_assemble.creator = this.name
      },
      /**
       * 更新测试环境
       */
      updateenviroment_assemble() {
        this.$refs.tmpenviroment_assemble.validate(valid => {
          if (valid) {
            updateenviroment_assemble(this.tmpenviroment_assemble).then(() => {
              this.$message.success('更新成功')
              this.getenviroment_assembleList()
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
      removeenviroment_assemble(index) {
        this.$confirm('删除该测试环境？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.enviroment_assembleList[index].id
          removeenviroment_assemble(id).then(() => {
            this.$message.success('删除成功')
            this.getenviroment_assembleList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 测试环境是否唯一
       * @param 测试环境
       */
      isUniqueDetail(enviroment_assemble) {
        for (let i = 0; i < this.enviroment_assembleList.length; i++) {
          if (this.enviroment_assembleList[i].id !== enviroment_assemble.id) { // 排除自己
            if (this.enviroment_assembleList[i].enviroment_assemblename === enviroment_assemble.enviroment_assemblename) {
              this.$message.error('测试环境名已存在')
              return false
            }
          }
        }
        return true
      }
    }
  }
</script>
