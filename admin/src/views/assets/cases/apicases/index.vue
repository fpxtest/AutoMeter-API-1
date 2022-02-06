<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true">
        <el-form-item>
          <el-button
            type="success"
            size="mini"
            :loading="btnLoading"
            icon="el-icon-refresh"
            v-if="hasPermission('apicases:list')"
            @click.native.prevent="getapicasesList"
          >刷新
          </el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apicases:add')"
            @click.native.prevent="showAddapicasesDialog"
          >添加用例
          </el-button>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            v-if="hasPermission('apicases:add')"
            @click.native.prevent="showCopyapicasesDialog"
          >复制用例
          </el-button>
        </el-form-item>
        <span v-if="hasPermission('apicases:search')">
          <el-form-item>
            <el-select v-model="search.deployunitname" placeholder="发布单元" @change="deployunitselectChanged($event)">
              <el-option label="请选择" value />
              <div v-for="(depname, index) in deployunitList" :key="index">
                <el-option :label="depname.deployunitname" :value="depname.deployunitname"/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-select v-model="search.apiname" placeholder="api名">
              <el-option label="请选择" value />
              <div v-for="(api, index) in apiList" :key="index">
                <el-option :label="api.apiname" :value="api.apiname"/>
              </div>
            </el-select>
          </el-form-item>

          <el-form-item >
          <el-select v-model="search.casetype" placeholder="用例类型">
            <el-option label="请选择" value />
            <el-option label="功能" value="功能"></el-option>
            <el-option label="性能" value="性能"></el-option>
          </el-select>
         </el-form-item>

          <el-form-item>
            <el-input clearable v-model="search.casename" @keyup.enter.native="searchBy" placeholder="用例" style="width:150px">
            </el-input>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="searchBy" :loading="btnLoading">查询</el-button>
          </el-form-item>
        </span>
      </el-form>
    </div>
    <el-table
      :data="apicasesList"
      :key="itemKey"
      v-loading.body="listLoading"
      element-loading-text="loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="编号" align="center" width="45">
        <template slot-scope="scope">
          <span v-text="getIndex(scope.$index)"></span>
        </template>
      </el-table-column>

      <el-table-column label="用例名" align="center" prop="casename" width="120"/>
      <el-table-column label="发布单元" align="center" prop="deployunitname" width="120"/>
      <el-table-column label="API" align="center" prop="apiname" width="120"/>
<!--      <el-table-column label="Jmeter-Class" align="center" prop="casejmxname" width="100"/>-->
      <el-table-column label="类型" align="center" prop="casetype" width="50"/>
      <el-table-column label="线程" align="center" prop="threadnum" width="50"/>
      <el-table-column label="循环" align="center" prop="loops" width="50"/>
      <el-table-column label="用例描述" align="center" prop="casecontent" width="120"/>
      <el-table-column label="操作人" align="center" prop="creator" width="60"/>
      <el-table-column label="创建时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.createTime) }}</template>
      </el-table-column>
      <el-table-column label="最后修改时间" align="center" prop="lastmodifyTime" width="120">
        <template slot-scope="scope">{{ unix2CurrentTime(scope.row.lastmodifyTime) }}
        </template>
      </el-table-column>
      <el-table-column label="管理" align="center"
                       v-if="hasPermission('apicases:update')  || hasPermission('apicases:delete')">
        <template slot-scope="scope">
<!--          <el-button-->
<!--            type="primary"-->
<!--            size="mini"-->
<!--            v-if="hasPermission('apicases:params') && scope.row.id !== id"-->
<!--            @click.native.prevent="showUpdateapicasesparamsDialog(scope.$index)"-->
<!--          >用例值-->
<!--          </el-button>-->
          <el-button
            type="warning"
            size="mini"
            v-if="hasPermission('apicases:update') && scope.row.id !== id"
            @click.native.prevent="showUpdateapicasesDialog(scope.$index)"
          >修改
          </el-button>
          <el-button
            type="danger"
            size="mini"
            v-if="hasPermission('apicases:delete') && scope.row.id !== id"
            @click.native.prevent="removeapicases(scope.$index)"
          >删除
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('apicases:params') && scope.row.id !== id"
            @click.native.prevent="showAssertDialog(scope.$index)"
          >断言
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('apicases:params') && scope.row.id !== id"
            @click.native.prevent="showTestDialog(scope.$index)"
          >调试
          </el-button>
          <el-button
            type="primary"
            size="mini"
            v-if="hasPermission('apicases:params') && scope.row.id !== id"
            @click.native.prevent="showcasedataDialog(scope.$index)"
          >用例值
          </el-button>
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
        style="width: 400px; margin-left:50px;"
        :model="tmpapicases"
        ref="tmpapicases"
      >
        <el-form-item label="发布单元" prop="deployunitname" required >
          <el-select v-model="tmpapicases.deployunitname" style="width:100%" placeholder="发布单元" @change="selectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(depunitname, index) in deployunitList" :key="index">
              <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="API" prop="apiname" required >
          <el-select v-model="tmpapicases.apiname" style="width:100%" placeholder="API" @change="apiselectChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(apiname, index) in apiList" :key="index">
              <el-option :label="apiname.apiname" :value="apiname.apiname" required/>
            </div>
          </el-select>
        </el-form-item>
        <el-form-item label="用例类型" prop="casetype" required >
          <el-select v-model="tmpapicases.casetype" style="width:100%" placeholder="用例类型" @change="funorperformChanged($event)">
            <el-option label="功能" value="功能"></el-option>
            <el-option label="性能" value="性能"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="用例名" prop="casename" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model.trim="tmpapicases.casename"
          />
        </el-form-item>

        <div v-if="threadloopvisible">
        <el-form-item label="线程数" prop="threadnum" required>
          <el-input
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.threadnum"
          />
        </el-form-item>

        <el-form-item label="线程循环" prop="loops" required>
          <el-input
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.loops"
          />
        </el-form-item>
        </div>

        <div v-if="JmeterClassVisible">
        <el-form-item label="Jmeter" prop="casejmxname" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.casejmxname"
          />
        </el-form-item>
        </div>

        <el-form-item label="用例描述" prop="casecontent" required>
          <el-input
            type="text"
            maxlength="50"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpapicases.casecontent"
          />
        </el-form-item>
        <el-form-item label="优先级" prop="level" >
          <el-input
            type="text"
            maxlength="20"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapicases.level"
          />
        </el-form-item>
        <el-form-item label="备注" prop="memo" >
          <el-input
            type="text"
            maxlength="100"
            prefix-icon="el-icon-message"
            auto-complete="off"
            v-model="tmpapicases.memo"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="dialogFormVisible = false">取消</el-button>
        <el-button
          type="danger"
          v-if="dialogStatus === 'add'"
          @click.native.prevent="$refs['tmpapicases'].resetFields()"
        >重置
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'add'"
          :loading="btnLoading"
          @click.native.prevent="addapicases"
        >添加
        </el-button>
        <el-button
          type="success"
          v-if="dialogStatus === 'update'"
          :loading="btnLoading"
          @click.native.prevent="updateapicases"
        >修改
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :title="paramtitle" :visible.sync="paramdialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 400px; margin-left:50px;"
        :model="tmpapicasesdata"
        ref="tmpapicasesdata"
      >
        <el-form-item label="用例名">
          <el-input
            readonly="true"
            v-model="tmpapicases.casename"
          />
        </el-form-item>
        <el-form-item label="参数类型" prop="propertytype" required >
          <el-select v-model="tmpapicasesdata.propertytype" placeholder="参数类型" style="width:100%" @change="selectparamsChanged($event)">
            <el-option label="请选择" value="''" style="display: none" />
            <div v-for="(paramtype, index) in caseparamtypelist" :key="index">
              <el-option :label="paramtype.propertytype" :value="paramtype.propertytype" required/>
            </div>
          </el-select>
        </el-form-item>

        <div v-if="HeaderandParamsVisible" >
          <el-form-item
            v-for="(param, index) in tmpcaseparamslist"
            :label="param"
            :key="index"
          >
            <el-input
              type="text"
              prefix-icon="el-icon-edit"
              v-model="paraList[index]"
            />
          </el-form-item>
        </div>

        <div v-if="BodyVisible">
          <el-form-item label="Body值：" prop="keyname" required>
            <el-input
              type="textarea"
              rows="30" cols="50"
              prefix-icon="el-icon-message"
              auto-complete="off"
              v-model.trim="tmpapicasesdata.keyname"
              :placeholder="keyholder"
            />
          </el-form-item>
        </div>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="paramdialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addapicasesdata"
        >保存
        </el-button>
      </div>
    </el-dialog>
    <el-dialog title="用例值" :visible.sync="casedataialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="80px"
        style="width: 600px; margin-left:50px;"
        :model="tmpapicasesdata"
        ref="tmpapicasesdata"
      >
        <el-form-item label="用例名:">
          <el-input
            readonly="true"
            v-model="tmpapicases.casename"
          />
        </el-form-item>

        <template>
          <el-tabs v-model="activeName" type="card" ref="tabs">
            <el-tab-pane label="Header" name="zero">
              <template>
                <el-table :data="Headertabledatas" border>
                  <el-table-column label="参数" prop="apiparam" align="center">
                    <template slot-scope="scope">
                      <el-input size="mini" readonly="true" v-model="scope.row.apiparam"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="值" prop="apiparamvalue" align="center">
                    <template slot-scope="scope">
                      <el-input size="mini" placeholder="值" v-model="scope.row.apiparamvalue"></el-input>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-tab-pane>
            <el-tab-pane label="Params" name="first">
              <template>
                <el-table :data="Paramstabledatas" border>
                  <el-table-column label="参数"  align="center">
                    <template slot-scope="scope">
                      <el-input size="mini" readonly="true" v-model="scope.row.apiparam"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="值类型"  align="center">
                    <template slot-scope="scope">
                      <el-input size="mini" readonly="true" v-model="scope.row.paramstype"></el-input>
                    </template>
                  </el-table-column>
                  <el-table-column label="值"  align="center">
                    <template slot-scope="scope">
                      <el-input size="mini" placeholder="值" v-model="scope.row.apiparamvalue"></el-input>
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-tab-pane>
            <el-tab-pane label="Body" name="second">
              <template>
                <div v-if="BodyParamDataVisible">
                  <el-table :data="Bodytabledatas" border>
                    <el-table-column label="参数"  align="center">
                      <template slot-scope="scope">
                        <el-input size="mini" placeholder="参数名" v-model="scope.row.apiparam"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="值类型"  align="center">
                      <template slot-scope="scope">
                        <el-input size="mini" readonly="true" v-model="scope.row.paramstype"></el-input>
                      </template>
                    </el-table-column>
                    <el-table-column label="默认值"  align="center">
                      <template slot-scope="scope">
                        <el-input size="mini" placeholder="默认值" v-model="scope.row.apiparamvalue"></el-input>
                      </template>
                    </el-table-column>
                  </el-table>
                </div>
                <div v-if="BodyDataVisible">
                    <el-form-item label="Body值：" prop="apiparamvalue" >
                      <el-input
                        type="textarea"
                        rows="20" cols="70"
                        prefix-icon="el-icon-message"
                        auto-complete="off"
                        v-model.trim="tmpapicasesbodydata.apiparamvalue"
                      />
                    </el-form-item>
                </div>
              </template>
            </el-tab-pane>
          </el-tabs>
        </template>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="casedataialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="addnewapicasesdata"
        >保存
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :title="CopyApiCase" :visible.sync="CopydialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="120px"
        style="width: 400px; margin-left:50px;"
        :model="tmpcopycase"
        ref="tmpcopycase"
      >      <el-form-item label="源发布单元" prop="sourcedeployunitname" required >
        <el-select v-model="tmpcopycase.sourcedeployunitname" placeholder="发布单元" style="width:100%" @change="CopyCasesSourceDeployselectChanged($event)">
          <el-option label="请选择" value="''" style="display: none" />
          <div v-for="(depunitname, index) in deployunitList" :key="index">
            <el-option :label="depunitname.deployunitname" :value="depunitname.deployunitname" required/>
          </div>
        </el-select>
      </el-form-item>

      <el-form-item label="用例来源" prop="sourcecasename" required >
        <el-select v-model="tmpcopycase.sourcecasename" placeholder="用例" style="width:100%" @change="CopySourceCasesChanged($event)">
          <el-option label="请选择" value="''" style="display: none" />
          <div v-for="(testcase, index) in sourcetestcaseList" :key="index">
            <el-option :label="testcase.casename" :value="testcase.casename" required/>
          </div>
        </el-select>
      </el-form-item>
      <el-form-item label="新用例名" prop="newcasename" required>
        <el-input
          type="text"
          maxlength="40"
          prefix-icon="el-icon-edit"
          auto-complete="off"
          v-model="tmpcopycase.newcasename"
        />
      </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="CopydialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          @click.native.prevent="copycases"
        >保存
        </el-button>
      </div>
    </el-dialog>
    <el-dialog :title="loadassert" :visible.sync="AssertdialogFormVisible">
      <div class="filter-container" >
        <el-form :inline="true" :model="searchassert" ref="searchcase" >

          <el-form-item>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-plus"
            @click.native.prevent="showAddassertDialog"
          >添加断言
          </el-button>
          </el-form-item>

          <el-form-item label="断言类型:"  prop="asserttype" required>
            <el-select v-model="searchassert.asserttype" placeholder="断言类型" >
              <el-option label="Respone断言" value="Respone"></el-option>
              <el-option label="Json断言" value="Json"></el-option>
              <el-option label="Xml断言" value="Xml"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="searchassertBy" :loading="btnLoading">查询</el-button>
          </el-form-item>

        </el-form>
      </div>
      <el-table
        ref="assertTable"
        :data="assertList"
        :key="assertitemKey"
        element-loading-text="loading"
        border
        fit
        highlight-current-row
      >
        <el-table-column label="编号" align="center" width="60">
          <template slot-scope="scope">
            <span v-text="assertgetIndex(scope.$index)"></span>
          </template>
        </el-table-column>
        <el-table-column label="断言类型"  align="center" prop="asserttype" width="80"/>
        <el-table-column label="断言子类型"  align="center" prop="assertsubtype" width="90"/>
        <el-table-column label="表达式"  align="center" prop="expression" width="100"/>
        <el-table-column label="条件" align="center" prop="assertcondition" width="60"/>
        <el-table-column label="断言值"  align="center" prop="assertvalues" width="80"/>
        <el-table-column label="断言值类型"  align="center" prop="assertvaluetype" width="100"/>
        <el-table-column label="管理" align="center">
          <template slot-scope="scope">
            <el-button
              type="warning"
              size="mini"
              @click.native.prevent="showUpdateapicasesassertDialog(scope.$index)"
            >修改
            </el-button>
            <el-button
              type="danger"
              size="mini"
              @click.native.prevent="removeapicasesassert(scope.$index)"
            >删除
            </el-button>

          </template>
        </el-table-column>

      </el-table>
      <el-pagination
        @size-change="asserthandleSizeChange"
        @current-change="asserthandleCurrentChange"
        :current-page="searchassert.page"
        :page-size="searchassert.size"
        :total="asserttotal"
        :page-sizes="[10, 20, 30, 40]"
        layout="total, sizes, prev, pager, next, jumper"
      ></el-pagination>
    </el-dialog>
    <el-dialog :title="AsserttextMap[AssertdialogStatus]" :visible.sync="AssertAUdialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="150px"
        style="width: 400px; margin-left:50px;"
        :model="tmpassert"
        ref="tmpassert"
      >
      <el-form-item label="断言类型" prop="asserttype" required >
        <el-select v-model="tmpassert.asserttype" style="width:100%" placeholder="断言类型" @change="asserttypeselectChanged($event)">
          <el-option label="Respone断言" value="Respone"></el-option>
          <el-option label="Json断言" value="Json"></el-option>
          <el-option label="Xml断言" value="Xml"></el-option>
        </el-select>
      </el-form-item>

        <div v-if="AssertSubVisible">
        <el-form-item label="断言子类型" prop="assertsubtype" required >
          <el-select v-model="tmpassert.assertsubtype" style="width:100%" placeholder="断言子类型">
            <el-option label="Code" value="Code"></el-option>
            <el-option label="文本" value="文本"></el-option>
          </el-select>
        </el-form-item>
        </div>

        <div v-if="ExpressionVisible">
        <el-form-item label="表达式" prop="expression" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpassert.expression"
          />
          <div class="right">
            <el-tooltip placement="right-end">
              <div slot="content">1.如果断言类型是Json则使用JsonPath表示, 例如：$.store.book[0].title  在线解析网站：http://www.e123456.com/aaaphp/online/jsonpath/<br/>2.如果断言类型为XML，则使用XPath表示， 例如：//div/h3//text()=hello|//div/h4//text()   在线解析网站： http://www.ab173.com/other/xpath.php</div>
              <el-button>表达式语法</el-button>
            </el-tooltip>
          </div>
        </el-form-item>
        </div>

        <el-form-item label="条件" prop="assertcondition" required >
          <el-select v-model="tmpassert.assertcondition" style="width:100%" placeholder="条件" @change="assertnameselectChanged($event)">
            <el-option label="等于" value="="></el-option>
            <el-option label="大于" value=">"></el-option>
            <el-option label="小于" value="<"></el-option>
            <el-option label="包含" value="Contain"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="断言值" prop="assertvalues" required>
          <el-input
            type="text"
            maxlength="40"
            prefix-icon="el-icon-edit"
            auto-complete="off"
            v-model="tmpassert.assertvalues"
          />
        </el-form-item>
        <el-form-item label="断言值类型" prop="assertvaluetype" required >
          <el-select v-model="tmpassert.assertvaluetype" style="width:100%" placeholder="断言值类型">
            <el-option label="int" value="int"></el-option>
            <el-option label="Long" value="Long"></el-option>
            <el-option label="Float" value="Float"></el-option>
            <el-option label="Double" value="Double"></el-option>
            <el-option label="Decimal" value="Decimal"></el-option>
            <el-option label="字符串" value="String"></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click.native.prevent="AssertAUdialogFormVisible = false">取消</el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          v-if="AssertdialogStatus === 'add'"
          @click.native.prevent="addassert"
        >保存
        </el-button>
        <el-button
          type="success"
          :loading="btnLoading"
          v-if="AssertdialogStatus === 'update'"
          @click.native.prevent="updateassert"
        >更新
        </el-button>
      </div>
    </el-dialog>
    <el-dialog title="调试" :visible.sync="TestdialogFormVisible">
      <el-form
        status-icon
        class="small-space"
        label-position="left"
        label-width="100px"
        style="width: 600px; margin-left:50px;"
      >
        <div class="filter-container">
          <el-form :inline="true" :model="tmptest" ref="tmptest">
            <el-form-item label="用例名：">
              <el-input style="width:500px" readonly="true" v-model="tmpapicases.casename"/>
            </el-form-item>

            <el-form-item label="环境 ：" prop="enviromentname"  required>
              <el-select style="width:330px" v-model="tmptest.enviromentname"  placeholder="环境" @change="EnviromentselectChanged($event)" >
                <el-option label="请选择"  />
                <div v-for="(enviroment, index) in enviromentnameList" :key="index">
                  <el-option :label="enviroment.enviromentname" :value="enviroment.enviromentname" required />
                </div>
              </el-select>
              <el-checkbox v-model="checked" @change="runprexchange">执行前置条件</el-checkbox>
              <el-button type="primary" :loading="btnLoading" @click.native.prevent="runtest">调试</el-button>
            </el-form-item>

            <template>
              <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
                <el-tab-pane label="通用" name="zero">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.general"
                  />
                </el-tab-pane>

                <el-tab-pane label="响应内容" name="first">
                    <el-input
                      type="textarea"
                      style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                      rows="20" cols="90"
                      maxlength="4000"
                      v-model="tmptest.respone"
                    />
                </el-tab-pane>

                <el-tab-pane label="请求Header" name="seven">
                  <el-table
                    :data="requestHeadList"
                    element-loading-text="loading"
                    border
                    fit
                    highlight-current-row
                  >
                    <el-table-column label="Name" align="center" prop="keyName" width="250"/>
                    <el-table-column label="Value" align="center" prop="keyValue" width="350"/>
                  </el-table>
                </el-tab-pane>

                <el-tab-pane label="请求Params" name="nine">
                  <el-table
                    :data="requestParamsList"
                    element-loading-text="loading"
                    border
                    fit
                    highlight-current-row
                  >
                    <el-table-column label="Name" align="center" prop="keyName" width="250"/>
                    <el-table-column label="Value" align="center" prop="keyValue" width="350"/>
                  </el-table>
                </el-tab-pane>

                <el-tab-pane label="请求Body" name="eight">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.requestdata"
                  />
                </el-tab-pane>

                <el-tab-pane label="响应Header" name="five">
                  <el-table
                    :data="headerList"
                    element-loading-text="loading"
                    border
                    fit
                    highlight-current-row
                  >
                    <el-table-column label="Name" align="center" prop="name" width="250"/>
                    <el-table-column label="Value" align="center" prop="value" width="350"/>
                  </el-table>
                </el-tab-pane>

                <el-tab-pane label="响应码" name="second">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.code"
                  />
                </el-tab-pane>
                <el-tab-pane label="响应时间(ms)" name="third">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.responeTime"
                  />
                </el-tab-pane>
                <el-tab-pane label="大小(B)" name="fourth">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.size"
                  />
                </el-tab-pane>

                <el-tab-pane label="Cookies" name="six">
                  <el-input
                    type="textarea"
                    style="width: 100%;border: none;outline: none;resize:none;overflow:hidden" readonly
                    rows="20" cols="90"
                    maxlength="4000"
                    v-model="tmptest.size"
                  />
                </el-tab-pane>
              </el-tabs>
            </template>
          </el-form>
        </div>

      </el-form>
    </el-dialog>
  </div>
</template>
<script>
  import {
    runtest,
    search,
    addapicases,
    updateapicases,
    removeapicases,
    copycases,
    getcasebydeployunitid
  } from '@/api/assets/apicases'

  import { getenviromentallList as getenviromentallList } from '@/api/enviroment/testenviroment'
  import { addapicasesdata as addapicasesdata, getparamvaluebycaseidandtype as getparamvaluebycaseidandtype, casevalueforbody as casevalueforbody, updatepropertydata, updateapicasesdata } from '@/api/assets/apicasesdata'
  import { getapiListbydeploy as getapiListbydeploy, getapi } from '@/api/deployunit/api'
  import { getcaseparatype as getcaseparatype } from '@/api/deployunit/apiparams'
  import { getdepunitLists as getdepunitLists, findDeployNameValueWithCode as findDeployNameValueWithCode } from '@/api/deployunit/depunit'
  import { unix2CurrentTime } from '@/utils'
  import { addapicasesassert, getassertbycaseid as getassertbycaseid, searchassert as searchassert, removeapicasesassert, updateapicasesassert } from '@/api/assets/apicasesassert'
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
        Headertabledatas: [],
        Paramstabledatas: [],
        Bodytabledatas: [],
        apicasesList: [],
        checked: 'false',
        activeName: 'zero',
        itemKey: null,
        assertitemKey: null,
        tmpasserttype: null,
        tmpprotocal: null,
        tmpdeployunitname: null,
        tmpapiname: null,
        tmpcasetype: null,
        tmpcasename: null,
        paraList: [], // paraList参数值列表
        paravaluemap: [], // 参数值map
        multipleSelection: [], // 用例表格被选中的内容
        apiList: [], // api列表
        enviromentnameList: [], // 环境列表
        deployunitList: [], // 发布单元列表
        caseparamtypelist: [], // 用例参数类型列表
        caseparamsbytypelist: [], // 根据类型获取用例参数名列表
        tmpcaseparamslist: [], // 获取用例参数临时列表，getcaseparamsbytype（）调用
        sourcetestcaseList: [],
        assertList: [],
        headerList: [], // Header列表
        requestHeadList: [], // Header列表
        requestParamsList: [], // Params列表
        listLoading: false, // 数据加载等待动画
        threadloopvisible: false, // 线程，循环显示
        JmeterClassVisible: false, // JmeterClassVisible显示
        ExpressionVisible: false, // 断言表达式显示
        AssertSubVisible: false, // 断言子条件显示
        AssertdialogFormVisible: false,
        AssertAUdialogFormVisible: false,
        TestdialogFormVisible: false,
        HeaderandParamsVisible: false,
        casedataialogFormVisible: false,
        BodyVisible: false,
        BodyParamDataVisible: false,
        BodyDataVisible: false,
        caseindex: '',
        total: 0, // 数据总数
        asserttotal: 0, // 数据总数
        apiSearchQuery: {
          deployunitname: '', // 发布单元名
          apiname: '' // api名
        },
        apiQuery: {
          deployunitname: '' // 获取字典表入参
        },
        dialogStatus: 'add',
        AssertdialogStatus: 'add',
        paramtitle: '用例参数值',
        AssertTitle: '新增修改断言',
        CopyApiCase: '复制用例',
        loadassert: '断言',
        dialogFormVisible: false,
        CopydialogFormVisible: false,
        paramdialogFormVisible: false,
        textMap: {
          updateRole: '修改API用例',
          update: '修改API用例',
          add: '添加API用例'
        },
        AsserttextMap: {
          updateRole: '修改用例断言',
          update: '修改用例断言',
          add: '添加用例断言'
        },
        diclevelQuery: {
          page: 1, // 页码
          size: 10, // 每页数量
          diccode: 'casecondition' // 获取字典表入参
        },
        btnLoading: false, // 按钮等待动画
        tmpapicases: {
          id: '',
          apiid: '',
          deployunitid: '',
          casename: '',
          deployunitname: '',
          apiname: '',
          casejmxname: '',
          casecontent: '',
          casetype: '',
          threadnum: '',
          loops: '',
          expect: '',
          middleparam: '',
          level: '',
          memo: '',
          creator: ''
        },
        tmpapicasesdata: {
          id: '',
          caseid: '',
          casename: '',
          propertytype: '',
          memo: '',
          casedataMap: [],
          keyname: ''
        },
        tmpapicasesbodydata: {
          id: '',
          caseid: '',
          casename: '',
          propertytype: '',
          memo: '',
          apiparam: '',
          apiparamvalue: '',
          paramstype: ''
        },
        tmpcopycase: {
          sourcecaseid: '',
          sourcedeployunitid: '',
          sourcedeployunitname: '',
          sourcecasename: '',
          newcasename: ''
        },
        tmpassert: {
          id: '',
          caseid: '',
          asserttype: '',
          assertsubtype: '',
          expression: '',
          assertcondition: '',
          assertvalues: '',
          assertvaluetype: '',
          creator: ''
        },
        tmptest: {
          enviromentname: '',
          respone: '',
          code: '',
          responeTime: '',
          size: '',
          general: '',
          requestdata: ''
        },
        tmpheader: {
          name: '',
          value: ''
        },
        tmptestdata: {
          caseid: '',
          enviromentid: '',
          prixflag: ''
        },
        tmpapi: {
          id: '',
          deployunitid: '',
          deployunitname: '',
          apiname: '',
          visittype: '',
          requesttype: '',
          apistyle: '',
          path: '',
          requestcontenttype: '',
          responecontenttype: '',
          memo: '',
          creator: ''
        },
        casevalue: {
          apiid: '',
          caseid: '',
          propertytype: ''
        },
        search: {
          page: 1,
          size: 10,
          deployunitname: null,
          apiname: null,
          casetype: null,
          casename: null
        },
        searchassert: {
          page: 1,
          size: 10,
          asserttype: null,
          caseid: null
        }
      }
    },

    created() {
      this.getenviromentallList()
      this.getapicasesList()
      this.getdepunitLists()
    },

    computed: {
      ...mapGetters(['name', 'sidebar', 'avatar'])
    },

    methods: {
      unix2CurrentTime,

      handleClick(tab, event) {
      },

      change() {
        this.$forceUpdate()
      },

      /**
       * 获取Header参数
       */
      getheaderdatabycaseidandtype() {
        var casedata = { caseid: this.tmpapicases.id, propertytype: 'Header' }
        getparamvaluebycaseidandtype(casedata).then(response => {
          this.Headertabledatas = response.data.list
        }).catch(res => {
          this.$message.error('获取Header参数值失败')
        })
      },

      /**
       * 获取Params参数值
       */
      getparamdatabycaseidandtype(property) {
        var casedata = { caseid: this.tmpapicases.id, propertytype: 'Params' }
        getparamvaluebycaseidandtype(casedata).then(response => {
          this.Paramstabledatas = response.data.list
        }).catch(res => {
          this.$message.error('获取Params参数值失败')
        })
      },

      /**
       * 获取Body参数值
       */
      getbodydatabycaseidandtype(property) {
        var casedata = { caseid: this.tmpapicases.id, propertytype: 'Body' }
        getparamvaluebycaseidandtype(casedata).then(response => {
          this.Bodytabledatas = response.data.list
        }).catch(res => {
          this.$message.error('获取Body参数值失败')
        })
      },

      /**
       * 获取Body-文本参数值
       */
      getbodytextdatabycaseidandtype() {
        var casedata = { caseid: this.tmpapicases.id, propertytype: 'Body' }
        getparamvaluebycaseidandtype(casedata).then(response => {
          console.log(response.data.list)
          if (response.data.list.length > 0) {
            this.tmpapicasesbodydata = response.data.list[0]
            console.log(this.tmpapicasesbodydata)
          } else {
            this.tmpapicasesbodydata.apiparamvalue = ''
          }
        }).catch(res => {
          this.$message.error('获取Body文本参数值失败')
        })
      },
      handleClickTableRow(row, event, column) {
        // this.$refs.fileTable.toggleRowSelection(row)
      },
      handleSelectionChange(rows) {
        this.multipleSelection = rows
      },
      runprexchange(e) {
        this.checked = e
        this.tmptestdata.prixflag = e
      },
      /**
       * 功能性能选择  e的值为options的选值
       */
      funorperformChanged(e) {
        if (e === '性能') {
          this.threadloopvisible = true
          this.tmpapicases.threadnum = ''
          this.tmpapicases.loops = ''
        } else {
          this.threadloopvisible = false
          this.tmpapicases.threadnum = 1
          this.tmpapicases.loops = 1
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectparamsChanged(e) {
        this.getcaseparamsbytype(e)
      },

      EnviromentselectChanged(e) {
        this.tmptest.respone = ''
        for (let i = 0; i < this.enviromentnameList.length; i++) {
          if (this.enviromentnameList[i].enviromentname === e) {
            this.tmptestdata.enviromentid = this.enviromentnameList[i].id
          }
        }
      },

      asserttypeselectChanged(e) {
        if (e === 'Respone') {
          this.ExpressionVisible = false
          this.AssertSubVisible = true
        } else {
          this.AssertSubVisible = false
          this.ExpressionVisible = true
        }
        this.tmpassert.assertsubtype = ''
        this.tmpassert.assertcondition = ''
        this.tmpassert.expression = ''
        this.tmpassert.assertvalues = ''
      },
      /**
       * 参数类型下拉框的值为e,来获取参数值
       */
      getcaseparamsbytype(e) {
        if (e === 'Body') {
          console.log('Body的数据，如果没有用例值，则从参数中获取，如果有，则永远取用例中的数据')
          this.HeaderandParamsVisible = false
          this.BodyVisible = true
          this.casevalue.apiid = this.apicasesList[this.caseindex].apiid
          this.casevalue.caseid = this.apicasesList[this.caseindex].id
          this.casevalue.propertytype = e
          casevalueforbody(this.casevalue).then(response => {
            this.tmpapicasesdata.keyname = response.data
          }).catch(res => {
            this.$message.error()
          })
        } else {
          this.HeaderandParamsVisible = true
          this.BodyVisible = false
          this.tmpcaseparamslist = null
          // this.paraList = null
          this.paravaluemap === null
          for (let i = 0; i < this.caseparamsbytypelist.length; i++) {
            if (this.caseparamsbytypelist[i].propertytype === e) {
              this.tmpcaseparamslist = this.caseparamsbytypelist[i].keyname.split(',')
              // todo 根据参数类型获取已存在的数据，用例id，参数类型
              this.casevalue.caseid = this.apicasesList[this.caseindex].id
              this.casevalue.propertytype = e
              getparamvaluebycaseidandtype(this.casevalue).then(response => {
                this.paraList = []
                this.paravaluemap = new Map()
                for (let j = 0; j < response.data.list.length; j++) {
                  this.paravaluemap.set(response.data.list[j].apiparam, response.data.list[j].apiparamvalue)
                }
                for (let k = 0; k < this.tmpcaseparamslist.length; k++) {
                  if (this.paravaluemap.has(this.tmpcaseparamslist[k])) {
                    this.paraList.push(this.paravaluemap.get(this.tmpcaseparamslist[k]))
                  } else {
                    this.paraList.push('')
                  }
                  console.log(this.paraList)
                }
                if (this.paraList === null) {
                  this.paraList = new Array(this.tmpcaseparamslist.length)
                }
              }).catch(res => {
                this.$message.error()
              })
            }
          }
        }
      },
      /**
       * 获取环境列表
       */
      getenviromentallList() {
        getenviromentallList().then(response => {
          this.enviromentnameList = response.data
        }).catch(res => {
          this.$message.error('加载环境列表失败')
        })
      },
      /**
       * 获取用例列表
       */
      getapicasesList() {
        this.listLoading = true
        this.search.deployunitname = this.tmpdeployunitname
        this.search.apiname = this.tmpapiname
        this.search.casetype = this.tmpcasetype
        this.search.casename = this.tmpcasename
        search(this.search).then(response => {
          this.apicasesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('加载用例列表失败')
        })
      },

      getassertbycaseid() {
        getassertbycaseid(this.searchassert).then(response => {
          this.assertList = response.data.list
          this.asserttotal = response.data.total
        }).catch(res => {
          this.$message.error('加载用例断言列表失败')
        })
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      selectChanged(e) {
        this.apiList = []
        this.tmpapicases.apiname = ''
        this.apiQuery.deployunitname = e
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpapicases.deployunitid = this.deployunitList[i].id
          }
        }
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
        // 获取发布单元对应的协议，是http或者https则不需要显示JmeterClass，其余显示
        findDeployNameValueWithCode(this.apiQuery).then(response => {
          this.tmpprotocal = response.data.protocal
          if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
            this.JmeterClassVisible = false
          } else {
            this.JmeterClassVisible = true
            this.tmpapicases.casejmxname = ''
          }
        }).catch(res => {
          this.$message.error('加载发布单元信息失败')
        })
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值,获取用例
       */
      CopyCasesSourceDeployselectChanged(e) {
        for (let i = 0; i < this.deployunitList.length; i++) {
          if (this.deployunitList[i].deployunitname === e) {
            this.tmpcopycase.sourcedeployunitid = this.deployunitList[i].id
          }
        }
        getcasebydeployunitid(this.tmpcopycase).then(response => {
          this.sourcetestcaseList = response.data
        }).catch(res => {
          this.$message.error('根据发布单元id获取用例列表失败')
        })
      },

      /**
       * 源用例下拉选择事件获取用例id  e的值为options
       */
      CopySourceCasesChanged(e) {
        for (let i = 0; i < this.sourcetestcaseList.length; i++) {
          if (this.sourcetestcaseList[i].casename === e) {
            this.tmpcopycase.sourcecaseid = this.sourcetestcaseList[i].id
          }
        }
      },

      /**
       * api下拉选择事件获取apiid  e的值为options的选值
       */
      apiselectChanged(e) {
        this.apiSearchQuery.apiname = e
        for (let i = 0; i < this.apiList.length; i++) {
          if (this.apiList[i].apiname === e) {
            console.log(this.tmpapicases.apiid)
            this.tmpapicases.apiid = this.apiList[i].id
          }
        }
      },

      /**
       * 发布单元下拉选择事件获取发布单元id  e的值为options的选值
       */
      deployunitselectChanged(e) {
        this.apiList = []
        this.search.apiname = ''
        this.apiQuery.deployunitname = e
        getapiListbydeploy(this.apiQuery).then(response => {
          this.apiList = response.data
        }).catch(res => {
          this.$message.error('加载api列表失败')
        })
      },

      /**
       * 获取发布单元列表
       */
      getdepunitLists() {
        getdepunitLists().then(response => {
          this.deployunitList = response.data
        }).catch(res => {
          this.$message.error('加载发布单元列表失败')
        })
      },

      searchBy() {
        this.search.page = 1
        this.listLoading = true
        search(this.search).then(response => {
          this.itemKey = Math.random()
          this.apicasesList = response.data.list
          this.total = response.data.total
          this.listLoading = false
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpdeployunitname = this.search.deployunitname
        this.tmpapiname = this.search.apiname
        this.tmpcasetype = this.search.casetype
        this.tmpcasename = this.search.casename
      },

      /**
       * 改变每页数量
       * @param size 页大小
       */
      handleSizeChange(size) {
        this.search.page = 1
        this.search.size = size
        this.getapicasesList()
      },
      /**
       * 改变页码
       * @param page 页号
       */
      handleCurrentChange(page) {
        this.search.page = page
        this.getapicasesList()
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

      searchassertBy() {
        this.searchassert.page = 1
        this.searchassert.caseid = this.tmpassert.caseid
        searchassert(this.searchassert).then(response => {
          this.assertitemKey = Math.random()
          this.assertList = response.data.list
          this.asserttotal = response.data.total
        }).catch(res => {
          this.$message.error('搜索失败')
        })
        this.tmpasserttype = this.searchassert.asserttype
      },
      /**
       * 改变每页数量
       * @param size 页大小
       */
      asserthandleSizeChange(size) {
        this.searchassert.page = 1
        this.searchassert.size = size
        this.getassertbycaseid(this.tmpassert)
      },
      /**
       * 改变页码
       * @param page 页号
       */
      asserthandleCurrentChange(page) {
        this.searchassert.page = page
        this.getassertbycaseid(this.tmpassert)
      },
      /**
       * 表格序号
       * 可参考自定义表格序号
       * http://element-cn.eleme.io/#/zh-CN/component/table#zi-ding-yi-suo-yin
       * @param index 数据下标
       * @returns 表格序号
       */
      assertgetIndex(index) {
        return (this.searchassert.page - 1) * this.searchassert.size + index + 1
      },
      /**
       * 显示添加用例对话框
       */
      showAddapicasesDialog() {
        // 显示新增对话框
        this.dialogFormVisible = true
        this.dialogStatus = 'add'
        this.tmpapicases.id = ''
        this.tmpapicases.casename = ''
        this.tmpapicases.apiname = ''
        this.tmpapicases.casetype = ''
        this.tmpapicases.deployunitname = ''
        this.tmpapicases.casejmxname = ''
        this.tmpapicases.casecontent = ''
        this.tmpapicases.expect = ''
        this.tmpapicases.middleparam = ''
        this.tmpapicases.level = ''
        this.tmpapicases.memo = ''
        this.tmpapicases.creator = this.name
        console.log(this.name)
      },

      /**
       * 显示增加断言对话框
       */
      showAddassertDialog() {
        // 显示新增对话框
        this.AssertdialogStatus = 'add'
        this.AssertAUdialogFormVisible = true
        this.tmpassert.id = ''
        this.tmpassert.assertcondition = ''
        this.tmpassert.assertsubtype = ''
        this.tmpassert.asserttype = ''
        this.tmpassert.assertvalues = ''
        this.tmpassert.expression = ''
        this.tmpassert.assertvaluetype = ''
        this.tmpassert.creator = this.name
      },

      /**
       * 显示Copy用例对话框
       */
      showCopyapicasesDialog() {
        // 显示新增对话框
        this.CopydialogFormVisible = true
        this.tmpcopycase.newcasename = ''
        this.tmpcopycase.sourcedeployunitname = ''
        this.tmpcopycase.sourcecasename = ''
        this.tmpcopycase.sourcedeployunitid = ''
        this.tmpcopycase.sourcecaseid = ''
      },

      /**
       * 复制用例
       */
      copycases() {
        this.$refs.tmpcopycase.validate(valid => {
          if (valid) {
            this.btnLoading = true
            copycases(this.tmpcopycase).then(() => {
              this.$message.success('复制成功')
              this.getapicasesList()
              this.CopydialogFormVisible = false
              this.btnLoading = false
            }).catch(res => {
              this.$message.error('复制失败')
              this.btnLoading = false
            })
          }
        })
      },

      /**
       * 添加用例
       */
      addapicases() {
        this.$refs.tmpapicases.validate(valid => {
          if (valid) {
            this.btnLoading = true
            this.tmpapicases.expect = this.tmpapicases.expect.trim()
            if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
              this.tmpapicases.casejmxname = 'httpapi'
            }
            addapicases(this.tmpapicases).then(() => {
              this.$message.success('添加成功')
              this.getapicasesList()
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
       * 添加断言
       */
      addassert() {
        this.$refs.tmpassert.validate(valid => {
          if (valid) {
            addapicasesassert(this.tmpassert).then(() => {
              this.$message.success('添加断言成功')
              this.searchassert.caseid = this.tmpassert.caseid
              this.getassertbycaseid(this.searchassert)
              this.AssertAUdialogFormVisible = false
            }).catch(res => {
              this.$message.error('添加断言失败')
            })
          }
        })
      },

      /**
       * 调试
       */
      runtest() {
        this.$refs.tmptest.validate(valid => {
          if (valid) {
            runtest(this.tmptestdata).then(response => {
              this.tmptest.general = '1.请求地址：' + response.data.responeGeneral.url + '\n' + '2.协议：' + response.data.responeGeneral.protocal + '\n' + '3.请求风格：' + response.data.responeGeneral.apistyle + '\n' + '4.请求方法：' + response.data.responeGeneral.method
              this.tmptest.requestdata = response.data.responeGeneral.postData
              this.headerList = response.data.headerList
              this.requestHeadList = response.data.requestHeadList
              this.requestParamsList = response.data.requestParamsList
              this.tmptest.respone = response.data.responeContent
              this.tmptest.code = response.data.responeCode
              this.tmptest.responeTime = response.data.responeTime
              this.tmptest.size = response.data.size
            }).catch(res => {
              this.$message.error('调试失败')
            })
          }
        })
      },
      /**
       * 添加用例数据
       */
      addapicasesdata() {
        this.tmpapicasesdata.casedataMap = []
        this.$refs.tmpapicasesdata.validate(valid => {
          if (valid) {
            if (this.tmpapicasesdata.propertytype === 'Body') {
              var Bodyparadata = { caseid: this.apicasesList[this.caseindex].id, casename: this.apicasesList[this.caseindex].casename, apiparam: 'Body', apiparamvalue: this.tmpapicasesdata.keyname, propertytype: this.tmpapicasesdata.propertytype, memo: 'memo' }
              this.tmpapicasesdata.casedataMap.push(Bodyparadata)
              console.log('处理Body')
            } else {
              console.log('处理Header，Params')
              for (let i = 0; i < this.tmpcaseparamslist.length; i++) {
                for (let j = 0; j < this.paraList.length; j++) {
                  if (i === j) {
                    var paradata = { caseid: this.apicasesList[this.caseindex].id, casename: this.apicasesList[this.caseindex].casename, apiparam: this.tmpcaseparamslist[i], apiparamvalue: this.paraList[j], propertytype: this.tmpapicasesdata.propertytype, memo: 'memo' }
                    this.tmpapicasesdata.casedataMap.push(paradata)
                  }
                }
              }
            }
            // 再增加
            addapicasesdata(this.tmpapicasesdata).then(() => {
              this.$message.success('添加成功')
              this.btnLoading = false
              this.paramdialogFormVisible = false
            }).catch(res => {
              this.$message.error('添加失败')
              this.btnLoading = false
            })
          }
        })
      },

      /**
       * 保存用例数据
       */
      async addnewapicasesdata() {
        this.updateHeaderpropertydata(this.Headertabledatas)
        this.updateHeaderpropertydata(this.Paramstabledatas)
        await this.getapi()
        if (this.tmpapi.requestcontenttype === 'Form表单') {
          this.updateHeaderpropertydata(this.Bodytabledatas)
        } else {
          this.updateapicasesdata()
        }
        this.$message.success('保存成功')
        this.casedataialogFormVisible = false
      },

      updateapicasesdata() {
        this.tmpapicasesbodydata.caseid = this.tmpapicases.id
        this.tmpapicasesbodydata.casename = this.tmpapicases.casename
        this.tmpapicasesbodydata.apiparam = 'Body'
        this.tmpapicasesbodydata.paramstype = this.tmpapi.requestcontenttype
        this.tmpapicasesbodydata.propertytype = 'Body'
        updateapicasesdata(this.tmpapicasesbodydata).then(response => {
        }).catch(res => {
          this.$message.error('更新用例Body值失败')
        })
      },

      updateHeaderpropertydata(datas) {
        updatepropertydata(datas).then(response => {
        }).catch(res => {
          this.$message.error('更新用例Header,Params值失败')
        })
      },
      /**
       * 显示修改用例对话框
       * @param index 用例下标
       */
      showUpdateapicasesDialog(index) {
        this.dialogFormVisible = true
        this.dialogStatus = 'update'
        this.tmpapicases.id = this.apicasesList[index].id
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.tmpapicases.apiname = this.apicasesList[index].apiname
        this.tmpapicases.casetype = this.apicasesList[index].casetype
        this.tmpapicases.deployunitname = this.apicasesList[index].deployunitname
        this.tmpapicases.casejmxname = this.apicasesList[index].casejmxname
        this.tmpapicases.casecontent = this.apicasesList[index].casecontent
        this.tmpapicases.expect = this.apicasesList[index].expect
        this.tmpapicases.middleparam = this.apicasesList[index].middleparam
        this.tmpapicases.level = this.apicasesList[index].level
        this.tmpapicases.memo = this.apicasesList[index].memo
        this.tmpapicases.creator = this.name
        if (this.tmpapicases.casetype === '性能') {
          this.threadloopvisible = true
          this.tmpapicases.threadnum = this.apicasesList[index].threadnum
          this.tmpapicases.loops = this.apicasesList[index].loops
        } else {
          this.threadloopvisible = false
          this.tmpapicases.threadnum = 1
          this.tmpapicases.loops = 1
        }
      },

      async getapi() {
        await getapi(this.tmpapicases.apiid).then(response => {
          this.tmpapi = response.data
        }).catch(res => {
          this.$message.error('获取api失败')
        })
      },
      /**
       * 显示用例值对话框
       * @param index 用例下标
       */
      async showcasedataDialog(index) {
        this.activeName = 'zero'
        this.casedataialogFormVisible = true
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.tmpapicases.id = this.apicasesList[index].id
        this.tmpapicases.apiid = this.apicasesList[index].apiid
        this.getheaderdatabycaseidandtype()
        this.getparamdatabycaseidandtype()
        await this.getapi()
        if (this.tmpapi.requestcontenttype === 'Form表单') {
          this.BodyParamDataVisible = true
          this.BodyDataVisible = false
          // 获取Body参数数据
          this.getbodydatabycaseidandtype()
        } else {
          // 获取body文本数据
          this.BodyDataVisible = true
          this.BodyParamDataVisible = false
          this.getbodytextdatabycaseidandtype()
          //
        }
      },
      /**
       * 显示用例参数对话框
       * @param index 用例下标
       */
      showUpdateapicasesparamsDialog(index) {
        this.BodyVisible = false
        this.tmpapicasesdata.keyname = ''
        this.tmpapicasesdata.caseid = this.apicasesList[index].id
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.tmpapicases.deployunitname = this.apicasesList[index].deployunitname
        this.tmpapicases.apiname = this.apicasesList[index].apiname
        this.caseindex = index
        this.tmpcaseparamslist = null
        this.tmpapicasesdata.propertytype = null
        this.caseparamtypelist = null
        this.paramdialogFormVisible = true
        getcaseparatype(this.tmpapicases).then(response => {
          this.caseparamsbytypelist = response.data.list
          this.caseparamtypelist = this.caseparamsbytypelist
          console.log(this.caseparamtypelist)
          // 去重
          // const res = new Map()
          // this.caseparamtypelist.filter((arr) => !res.has(this.caseparamtypelist.propertytype) && res.set(this.caseparamtypelist.propertytype, 1))
        }).catch(res => {
          this.$message.error()
          // this.$message.error('获取用例参数类型失败')
        })
      },

      /**
       * 显示修改用例断言对话框
       * @param index 用例下标
       */
      showUpdateapicasesassertDialog(index) {
        this.AssertdialogStatus = 'update'
        this.AssertAUdialogFormVisible = true
        this.tmpassert.id = this.assertList[index].id
        this.tmpassert.asserttype = this.assertList[index].asserttype
        this.tmpassert.assertsubtype = this.assertList[index].assertsubtype
        this.tmpassert.expression = this.assertList[index].expression
        this.tmpassert.assertcondition = this.assertList[index].assertcondition
        this.tmpassert.assertvalues = this.assertList[index].assertvalues
        this.tmpassert.assertvaluetype = this.assertList[index].assertvaluetype
        this.tmpassert.creator = this.name
        if (this.tmpassert.asserttype === 'Respone') {
          this.ExpressionVisible = false
          this.AssertSubVisible = true
        } else {
          this.AssertSubVisible = false
          this.ExpressionVisible = true
        }
      },

      /**
       * 显示断言对话框
       */
      showAssertDialog(index) {
        this.tmpapicases.id = this.apicasesList[index].id
        this.tmpassert.caseid = this.tmpapicases.id
        this.searchassert.caseid = this.tmpassert.caseid
        this.getassertbycaseid(this.searchassert)
        this.AssertdialogFormVisible = true
        this.searchassert.asserttype = ''
      },

      /**
       * 显示断言对话框
       */
      showTestDialog(index) {
        this.tmptestdata.caseid = this.apicasesList[index].id
        this.tmptestdata.prixflag = this.checked
        this.tmpapicases.casename = this.apicasesList[index].casename
        this.activeName = 'zero'
        this.tmptest.general = ''
        this.headerList = null
        this.requestHeadList = null
        this.tmptest.size = ''
        this.tmptest.code = ''
        this.tmptest.responeTime = ''
        this.tmptest.enviromentname = ''
        this.tmptest.respone = ''
        this.TestdialogFormVisible = true
      },

      /**
       * 显示前置条件对话框
       */
      showpreconditionDialog() {
        if (this.multipleSelection.length > 1) {
          this.$message.error('不支持多个用例一起设置条件，选择单个设置')
        } else {
          this.preconditiondialogFormVisible = true
          this.tmpapicases.id = this.multipleSelection[0].id
        }
      },

      /**
       * 更新用例
       */
      updateapicases() {
        this.$refs.tmpapicases.validate(valid => {
          if (valid) {
            this.tmpapicases.expect = this.tmpapicases.expect.trim()
            if (this.tmpprotocal === 'http' || this.tmpprotocal === 'https') {
              this.tmpapicases.casejmxname = 'httpapi'
            }
            updateapicases(this.tmpapicases).then(() => {
              this.$message.success('更新成功')
              this.getapicasesList()
              this.dialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 更新用例断言
       */
      updateassert() {
        this.$refs.tmpassert.validate(valid => {
          if (valid) {
            updateapicasesassert(this.tmpassert).then(() => {
              this.$message.success('更新成功')
              this.getassertbycaseid()
              this.AssertAUdialogFormVisible = false
            }).catch(res => {
              this.$message.error('更新失败')
            })
          }
        })
      },

      /**
       * 删除用例
       * @param index 用例下标
       */
      removeapicases(index) {
        this.$confirm('删除该用例？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.apicasesList[index].id
          removeapicases(id).then(() => {
            this.$message.success('删除成功')
            this.getapicasesList()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 删除用例断言
       * @param index 用例下标
       */
      removeapicasesassert(index) {
        this.$confirm('删除该用例断言？', '警告', {
          confirmButtonText: '是',
          cancelButtonText: '否',
          type: 'warning'
        }).then(() => {
          const id = this.assertList[index].id
          removeapicasesassert(id).then(() => {
            this.$message.success('删除成功')
            this.getassertbycaseid()
          })
        }).catch(() => {
          this.$message.info('已取消删除')
        })
      },

      /**
       * 用例是否唯一
       * @param 用例
       */
      isUniqueDetail(apicases) {
        for (let i = 0; i < this.apicasesList.length; i++) {
          if (this.apicasesList[i].id !== apicases.id) { // 排除自己
            if (this.apicasesList[i].deployunitname === apicases.deployunitname) {
              if (this.apicasesList[i].apiname === apicases.apiname) {
                if (this.apicasesList[i].casename === apicases.casename) {
                  this.$message.error('用例名已存在')
                  return false
                }
              }
            }
          }
        }
        return true
      }
    }
  }
</script>
