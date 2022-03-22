<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true"
               :model="tmpquery"
               ref="tmpquery"
      >
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            icon="el-icon-refresh"
            v-if="hasPermission('apireport:list')"
            @click.native.prevent="getapireportList"
          >刷新</el-button>
        </el-form-item>

        <span v-if="hasPermission('apireport:search')">
          <el-form-item label="测试集合" prop="testplanname" required>
          <el-select v-model="tmpquery.testplanname" placeholder="测试集合" @change="testplanselectChanged($event)">
            <el-option label="请选择"/>
            <div v-for="(testplan, index) in execplanList" :key="index">
              <el-option :label="testplan.executeplanname" :value="testplan.executeplanname" />
            </div>
          </el-select>
        </el-form-item>
          <el-form-item label="执行计划" prop="batchname" required>
            <el-select v-model="tmpquery.batchname" placeholder="执行计划" @change="testbatchselectChanged($event)">
            <el-option label="请选择"/>
            <div v-for="(planbatch, index) in planbatchList" :key="index">
              <el-option :label="planbatch.batchname" :value="planbatch.batchname" />
            </div>
          </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="getfunctioncasestatics" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>

    <div class="dashboard-editor-container">
      <github-corner class="github-corner" />
      <el-row :gutter="60">
        <el-col :xs="24" :sm="24" :lg="9">
          <div id="22" class="chart-wrapper">
            <template>
              <div id="mian" style="width: 300px;height:250px;"></div>
            </template>
            <el-table
              style="width: 120vh"
              :data="casestaticsList"
              :key="itemKey"
              v-loading.body="listLoading"
              size="mini"
              element-loading-text="loading"
              border
              highlight-current-row
            >
              <el-table-column label="成功率" align="center" prop="successrate" width="160">
              </el-table-column>
              <el-table-column label="失败率" align="center" prop="failrate" width="200"/>
            </el-table>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="15">
          <div id="12" class="chart-wrapper">
            <el-table
              style="width: 290vh"
              :data="casestaticsList"
              v-loading.body="listLoading"
              element-loading-text="loading"
              border
              fit
              highlight-current-row
            >
              <el-table-column label="用例总数" align="center" prop="caseNum" width="150">
              </el-table-column>
              <el-table-column label="执行中数" align="center" prop="execCaseNums" width="130"/>
              <el-table-column label="成功数" align="center" prop="successCaseNums" width="130"/>
              <el-table-column label="失败数" align="center" prop="failCaseNums" width="150"/>
              <el-table-column label="未执行数" align="center" prop="notExecCaseNums" width="120"/>
            </el-table>
          </div>
        </el-col>
        <el-col :xs="24" :sm="24" :lg="8">
          <div id="13" class="chart-wrapper">
            <el-table
              style="width: 100vh"
              :data="casesconditiontaticsList"
              :key="itemKey"
              v-loading.body="listLoading"
              size="mini"
              element-loading-text="loading"
              border
              highlight-current-row
            >
              <el-table-column label="测试集合子条件" align="center" prop="caseConditionNums" width="150">
              </el-table-column>
              <el-table-column label="用例子条件" align="center" prop="testCollectionConditionsNUms" width="150"/>
            </el-table>
          </div>
        </el-col>
      </el-row>
    </div>

    <el-tabs v-model="activeName" type="card" ref="tabs">
      <el-tab-pane label="用例结果报告" name="zero">

        <div class="filter-container">
          <el-form :inline="true">

          <el-form-item label="状态:" prop="testplanname" >
              <el-select v-model="tmpquery.caseststus" style="width:100%" placeholder="值类型">
                <el-option label="成功" value="成功"></el-option>
                <el-option label="失败" value="失败"></el-option>
              </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="searchcaseReportBy" :loading="btnLoading">查询</el-button>
          </el-form-item>

          </el-form>
        </div>

        <template>
          <el-table
            :data="apireportList"
            :key="itemKey"
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
            <el-table-column label="发布单元" align="center" prop="deployunitname" width="120"/>


            <el-table-column label="请求地址" align="center" prop="url" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.url }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="请求头" align="center" prop="requestheader" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.requestheader }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="请求数据" align="center" prop="requestdatas" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top-start">
                  <p>{{ scope.row.requestdatas }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="响应" align="center" prop="respone" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.respone }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="断言" align="center" prop="expect" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.expect }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium">...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="断言结果" align="center" prop="assertvalue" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.assertvalue }}</p>
                  <div slot="reference" class="name-wrapper">
                    <el-tag size="medium" >...</el-tag>
                  </div>
                </el-popover>
              </template>
            </el-table-column>

            <el-table-column label="运行时间(ms)" align="center" prop="runtime" width="100"/>
            <el-table-column label="异常信息" align="center" prop="errorinfo" width="80">
              <template slot-scope="scope">
                <el-popover trigger="hover" placement="top">
                  <p>{{ scope.row.errorinfo }}</p>
                  <div v-if="scope.row.errorinfo !== ''" slot="reference" class="name-wrapper">
                    <el-tag size="medium" style="color:red">异常...</el-tag>
                  </div>
                  <div v-if="scope.row.errorinfo === ''" slot="reference" class="name-wrapper">
                    <el-tag size="medium" style="color:green">...</el-tag>
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
            :current-page="search.page"
            :page-size="search.size"
            :total="total"
            :page-sizes="[10, 20, 30, 40]"
            layout="total, sizes, prev, pager, next, jumper"
          ></el-pagination>
        </template>
      </el-tab-pane>
      <el-tab-pane label="条件报告" name="first">
        <template>
          <el-table
            style="width: 90vh"
            :data="apiList"
            :key="itemKey"
            v-loading.body="listLoading"
            size="mini"
            element-loading-text="loading"
            border
            highlight-current-row
          >
            <el-table-column label="用例数" align="center" width="100">
            </el-table-column>
            <el-table-column label="执行中" align="center" prop="apiname" width="120"/>
            <el-table-column label="成功数" align="center" prop="deployunitname" width="130"/>
            <el-table-column label="失败数" align="center" prop="apistyle" width="80"/>
            <el-table-column label="未执行数" align="center" prop="visittype" width="80"/>
          </el-table>
        </template>
      </el-tab-pane>
      <el-tab-pane label="条件变量值" name="second">
        <template>
          <el-table
            style="width: 90vh"
            :data="apiList"
            :key="itemKey"
            v-loading.body="listLoading"
            size="mini"
            element-loading-text="loading"
            border
            highlight-current-row
          >
            <el-table-column label="用例数" align="center" width="100">
            </el-table-column>
            <el-table-column label="执行中" align="center" prop="apiname" width="120"/>
            <el-table-column label="成功数" align="center" prop="deployunitname" width="130"/>
            <el-table-column label="失败数" align="center" prop="apistyle" width="80"/>
            <el-table-column label="未执行数" align="center" prop="visittype" width="80"/>
          </el-table>        </template>
      </el-tab-pane>
      <el-tab-pane label="异常用例" name="three">
        <template>
          <el-table
            style="width: 90vh"
            :data="apiList"
            :key="itemKey"
            v-loading.body="listLoading"
            size="mini"
            element-loading-text="loading"
            border
            highlight-current-row
          >
            <el-table-column label="用例数" align="center" width="100">
            </el-table-column>
            <el-table-column label="执行中" align="center" prop="apiname" width="120"/>
            <el-table-column label="成功数" align="center" prop="deployunitname" width="130"/>
            <el-table-column label="失败数" align="center" prop="apistyle" width="80"/>
            <el-table-column label="未执行数" align="center" prop="visittype" width="80"/>
          </el-table>
        </template>
      </el-tab-pane>

    </el-tabs>


  </div>
</template>

<script>
  // import PieChart from '@/views/dashboard/admin/components/PieChart'
  import { search as search, getfunctioncasestatics as getfunctioncasestatics, getfunctionconditionstatics as getfunctionconditionstatics, getfunctionCaseSandF as getfunctionCaseSandF, findApicasereportWithNameandStatus as findApicasereportWithNameandStatus } from '@/api/reportcenter/apireport'
  import { getbatchbyplan as getbatchbyplan } from '@/api/executecenter/executeplanbatch'
  import { getallexplanbytype as getallexplanbytype } from '@/api/executecenter/executeplan'
  import { unix2CurrentTime } from '@/utils'
  // import echarts from 'echarts'

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
    // components: {
    //   PieChart
    // },
    data() {
      return {
        activeName: 'zero',
        chartPie: null,
        seriesData: [],
        casestaticsList: [], // api报告列表
        casesconditiontaticsList: [], // api报告列表
        itemKey: null,
        tmptestplanname: '',
        tmptestplanid: null,
        tmpbatchname: null,
        apireportList: [], // api报告列表
        apiList: [], // api列表
        planbatchList: [], // 执行计划列表
        execplanList: [], // 计划列表
        deployunitList: [], // 发布单元列表
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
          updateRole: '修改apireport',
          update: '修改apireport',
          add: '添加apireport'
        },
        btnLoading: false, // 按钮等待动画
        tmpapireport: {
          executeplanid: '', // 计划id
          id: '',
          deployunitid: '',
          deployunitname: '',
          batchname: '',
          requestheader: '',
          requestdatas: '',
          visittype: '',
          path: '',
          url: '',
          requestmethod: '',
          memo: ''
        },
        tmpquery: {
          page: 1,
          size: 10,
          testplanname: '',
          executeplanid: '',
          batchid: '',
          batchname: '',
          caseststus: ''
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
    },

    mounted() {
      this.drawLine()
    },

    methods: {
      unix2CurrentTime,
      /**
       * 获取测试集合列表
       */
      drawLine() {
        const echarts = require('echarts')
        const myChart = echarts.init(document.getElementById('mian'))
        // 绘制图表
        var option
        option = {
          title: {
            text: '用例执行结果比例',
            // subtext: '纯属虚构',
            left: 'center'
          },
          tooltip: {
            trigger: 'item',
            formatter: '{a} <br/>{b} : {c} ({d}%)'
          },
          color: ['#44c23f', '#d7212f'],
          series: [
            {
              name: '类型',
              type: 'pie',
              radius: '75%',
              center: ['50%', '48%'],
              data: this.seriesData
            }
          ]
        }
        myChart.setOption(option)
      },
      getapireportList() {
        this.$refs.tmpquery.validate(valid => {
          if (valid) {
            search(this.tmpquery).then(response => {
              this.apireportList = response.data.list
              this.total = response.data.total
            }).catch(res => {
              this.$message.error('加载api报告列表失败')
            })
          }
        })
      },

      searchcaseReportBy() {
        this.$refs.tmpquery.validate(valid => {
          if (valid) {
            findApicasereportWithNameandStatus(this.tmpquery).then(response => {
              this.apireportList = response.data.list
              this.total = response.data.total
            }).catch(res => {
              this.$message.error('加载用例结果报告列表失败')
            })
          }
        })
      },
      getexecplanList() {
        this.tmpexecplantype.usetype = '功能'
        getallexplanbytype(this.tmpexecplantype).then(response => {
          this.execplanList = response.data
        }).catch(res => {
          this.$message.error('加载计划列表失败')
        })
      },

      async getfunctionCaseSandF() {
        await getfunctionCaseSandF(this.tmpquery).then(response => {
          this.seriesData = response.data
        }).catch(res => {
          this.$message.error('加载用例结果比例列表失败')
        })
      },

      async getfunctioncasestatics() {
        this.getapireportList()
        this.$refs.tmpquery.validate(valid => {
          if (valid) {
            getfunctioncasestatics(this.tmpquery).then(response => {
              this.casestaticsList = response.data
            }).catch(res => {
            })
          }
        })
        this.getfunctionconditionstatics()
        await this.getfunctionCaseSandF()
        this.drawLine()
        this.getapireportList()
      },

      getfunctionconditionstatics() {
        this.$refs.tmpquery.validate(valid => {
          if (valid) {
            getfunctionconditionstatics(this.tmpquery).then(response => {
              this.casesconditiontaticsList = response.data
              console.log(this.casesconditiontaticsList)
            }).catch(res => {
            })
          }
        })
      },
      /**
       * 计划下拉选择事件获取发布单元id  e的值为options的选值
       */
      testplanselectChanged(e) {
        for (let i = 0; i < this.execplanList.length; i++) {
          if (this.execplanList[i].executeplanname === e) {
            this.tmpquery.executeplanid = this.execplanList[i].id
          }
        }
        getbatchbyplan(this.tmpquery).then(response => {
          this.planbatchList = response.data
        }).catch(res => {
          this.$message.error('加载执行计划列表失败')
        })
      },

      testbatchselectChanged(e) {
        this.tmpquery.batchname = e
        for (let i = 0; i < this.planbatchList.length; i++) {
          if (this.planbatchList[i].batchname === e) {
            this.tmpquery.batchid = this.planbatchList[i].id
          }
        }
      },

      getIndex(index) {
        return (this.tmpquery.page - 1) * this.tmpquery.size + index + 1
      },
      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.tmpquery.page = 1
        this.tmpquery.size = size
        this.getapireportList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.tmpquery.page = page
        this.getapireportList()
      }

    }
  }
</script>



<style lang="scss" scoped>
.dashboard-editor-container {
  padding: 20px;
  background-color: rgb(240, 242, 245);
  position: relative;

  .github-corner {
    position: absolute;
    top: 0px;
    border: 0;
    right: 0;
  }

  .chart-wrapper {
    background: #fffffb;
    padding: 16px 16px 0;
    margin-bottom: 32px;
  }
}

@media (max-width:1024px) {
  .chart-wrapper {
    padding: 8px;
  }

  .chart-container{
    position: relative;
    width: 100%;
    height: calc(100vh - 84px);
  }
}
</style>
