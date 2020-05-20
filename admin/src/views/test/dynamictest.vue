<template>
  <div>


    <el-tabs v-model="Params" type="card" closable @tab-remove="removeTab" @tab-click="handleClick" :before-leave="beforeLeaveTab">
      <el-tab-pane
        v-for="(item) in editableTabs"
        :key="item.name"
        :label="item.title"
        :name="item.name"
      >
        <component :is=item.content></component>

        <el-form  :model="dynamicValidateForm" ref="dynamicValidateForm" label-width="50px" >
          <!--<el-form-item-->
          <!--prop="email"-->
          <!--label="邮箱"-->
          <!--:rules="[-->
          <!--{ required: true, message: '请输入邮箱地址', trigger: 'blur' },-->
          <!--{ type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }-->
          <!--]"-->
          <!--&gt;-->
          <!--<el-input v-model="dynamicValidateForm.email" style="width:200px"></el-input>-->
          <!--</el-form-item>-->
          <el-form-item
            v-for="(domain, index) in dynamicValidateForm.domains"
            :label="'key' + index"
            :key="domain.key"
            :prop="'domains.' + index + '.value'"
            :rules="{required: true, message: '域名不能为空', trigger: 'blur'}"
          >
            <el-input v-model="domain.value" style="width:200px"></el-input>
            <el-button type="danger"  @click.prevent="removeDomain(domain)">删除</el-button>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" @click="submitForm('dynamicValidateForm')">提交</el-button>
            <el-button @click="addDomain">新增key</el-button>
            <el-button @click="resetForm('dynamicValidateForm')">重置</el-button>
          </el-form-item>
        </el-form>

      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
  export default {
    data() {
      return {
        editableTabs: [{
          title: 'Header',
          name: '1',
          content: 'cccc'
        }, {
          title: 'Params',
          name: '2',
          content: ''
        }],
        tabIndex: 2,
        dynamicValidateForm: {
          domains: [{
            value: ''
          }],
          email: ''
        }
      }
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            alert('submit!')
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields()
      },
      removeDomain(item) {
        var index = this.dynamicValidateForm.domains.indexOf(item)
        if (index !== -1) {
          this.dynamicValidateForm.domains.splice(index, 1)
        }
      },
      addDomain() {
        this.dynamicValidateForm.domains.push({
          value: '',
          key: Date.now()
        })
      },

      handleClick(tab, event) {
        console.log(event)
        console.log(event.target.getAttribute('label'))
        console.log(event.target.getAttribute('title'))
      },

      beforeLeaveTab() {
        if (!this.isTip) {
          this.isTip = true
          return true
        }

        return this.$confirm('此操作将切换tab页, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$message({
            type: 'success',
            message: '切换成功！可以做一些其他的事情' + this.tabIndex
          })
        }).catch(() => {
          this.$message({
            type: 'success',
            message: '取消成功！可以做一些其他的事情'
          })
          throw new Error('取消成功！')
        })
      }
    }
  }
</script>
