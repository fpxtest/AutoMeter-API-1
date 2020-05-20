<template>
  <div>
    <!--头部-->
    <div>
      <div class="headerWrap">
        <el-form :inline="true" label-position="left">
          <el-form-item label="Table Name">
            <el-select class="tablenameWrap" v-model="theme" placeholder="请选择表名" size="medium">
              <el-option  :value="item" >11111</el-option>
              <el-option v-for="item in themetitle" :label="item.mastrNm" :value="item" :key="item.value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="medium" v-on:click="managerTable">查询</el-button>
          </el-form-item>
        </el-form>
      </div>
      <div class="updateDataWrap">
        <el-button size="medium" type="primary" @click="addRow">新增</el-button>
        <el-button size="medium" type="primary" @click="handleEdit">修改</el-button>
        <el-button size="medium" type="primary" @click="hanleSave">保存</el-button>
        <el-button size="medium" type="danger" @click="handleDel">删除</el-button>
      </div>
    </div>
    <!--表格-->
    <div>
      <template>
        <el-table
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange">
          <el-table-column v-if="seen" type="selection"></el-table-column>
          <el-table-column v-for="(value, key, index) in tableData[0]" :label="key" :key="index">
            <template slot-scope="scope">
              <el-form :rules="rules" ref=scope.row[key]>
                <span v-if="scope.row.seen"><el-input size="medium" v-model="scope.row[key]" type="text"/></span>
                <span v-if="selectData.indexOf(scope.row) == -1">{{scope.row[key]}}</span>
                <span v-else>
                  <el-input v-if="key!='PROD_ID'" size="medium" v-model="scope.row[key]" type="text"/>
                  <span v-else="">{{scope.row[key]}}</span>
                </span>
              </el-form>
            </template>
          </el-table-column>
        </el-table>
        {{tableData}}
        <div class="block" style="margin-top: 20px;text-align: center">
          <el-pagination background
                         layout="prev, pager, next"
                         :page-count="pagecount">
          </el-pagination>
        </div>
      </template>
    </div>
  </div>
</template>

import { mangerTable as mangerTable,registerList as registerList,addTable as addTable,editTable as editTable,delTable as delTable } from '@/api/test/testfun'


<script>
  export default {
    data() {
      return {
        themetitle: [],
        tableData: [],
        multipleSelection: [],
        indexRow: '',
        pagecount: 0,
        theme: {
          value: '',
          mastrNm: '',
          tableNm: ''
        },
        seen: false,
        selectData: [],
        saveType: -1
      }
    },
    created() {
      this.selectTableName()
    },
    methods: {
      /* 表格查询 */
      managerTable() {
        const theme = this.theme.tableNm
        if (theme === 0) {
          this.$message.warning('请选择表名!')
          return false
        } else {
          this.seen = true
          this.mangerTable(theme).then(res => {
            if (res.status === 200) {
              if (res.data.result === 4) {
                this.tableData = res.data.data
                this.pagecount = res.data.pageNo
                this.tableData = res.data.data
              } else {
                this.$message.error(res.data.msg)
              }
            }
          })
        }
      },
      selectTableName() {
        this.registerList().then(res => {
          const item = res.data.data
          item.forEach((t) => {
            this.themetitle.push({
              'mastrNm': t.bmtMastrNm,
              'tableNm': t.bmtTableNm,
              'value': t.bmtMastrId
            })
          })
        })
      },
      // 点击新增
      addRow() {
        if (this.tableData.length === 0) {
          this.$message.warning('请选择表名!')
          return false
        } else if (this.saveType === 1) {
          this.$message.warning('请先保存当前编辑项')
          return false
        } else {
          this.tableData.push({ 'seen': true })
          this.saveType = 0
        }
      },
      // 点击修改
      handleEdit() {
        if (this.tableData.length === 0) {
          this.$message.warning('请选择表名!')
          return false
        } else if (this.saveType === 0) {
          this.selectData = []
          this.$message.warning('请先保存当前编辑项')
          return false
        } else if (this.multipleSelection.length === 0) {
          this.$message.warning('请选择修改数据')
          return false
        } else {
          this.saveType = 1
          this.selectData = this.multipleSelection
        }
      },
      // 点击删除
      handleDel() {
        const delData = this.multipleSelection
        const param = delData.map(v => ({
          tableName: this.theme.tableNm,
          values: Object.values(v)
        }))
        this.delTable(param).then(res => {
          if (res.data.result === 4) {
            this.managerTable()
            this.$message.success('删除成功')
          } else {
            this.$message.error(res.data.msg)
          }
        })
      },
      // 点击保存
      hanleSave() {
        if (this.saveType === 0) {
          // 新增接口
          const addData = this.tableData.filter(function(ele) {
            if (ele.seen === true) {
              delete ele.seen
              return true
            }
          })
          var param = addData.map(v => ({
            tableName: this.theme.tableNm,
            values: Object.values(v)
          }))
          this.addTable(param).then(res => {
            if (res.data.result === 4) {
              this.saveType = -1
              this.selectData = []
              this.$message.success('保存成功！')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        } else if (this.saveType === 1) {
          // 修改接口
          const editData = this.selectData.map(v => ({
            tableName: this.theme.tableNm,
            values: Object.values(v)
          }))
          this.editTable(editData).then(res => {
            if (res.data.result === 4) {
              this.selectData = []
              this.saveType = -1
              this.$message.success('保存成功！')
            } else {
              this.$message.error(res.data.msg)
            }
          })
        }
      },
      handleSelectionChange(val) {
        this.multipleSelection = val
      },
      /* 分页方法 */
      handleSizeChange(val) {
      }
    }
  }
</script>

<style scoped>
  .el-dropdown {
    vertical-align: top;
  }

  .el-dropdown + .el-dropdown {
    margin-left: 15px;
  }

  .el-icon-arrow-down {
    font-size: 12px;
  }

  .headerWrap {
    height: 36px;
    line-height: 36px;
  }

  .tablenameWrap {
    width: 150px;
  }

  .updateDataWrap {
    margin: 20px 0;
  }

  .pagination {
    float: right;
    margin-top: 20px;
  }
</style>

