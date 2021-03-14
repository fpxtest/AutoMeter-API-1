<template>
  <div class='profile'>
    <el-main>
      <el-button type='primary' @click='centerDialogVisible = true'
      >弹框按钮</el-button
      >
    </el-main>
    <el-dialog
      title='增加删除元素'
      :visible.sync='centerDialogVisible'
      width='50%'
      center
    >
      <el-form
        :model='dynamicValidateForm'
        ref='dynamicValidateForm'
        label-width='100px'
        class='demo-dynamic'
      >
        <!--<el-form-item label='下拉框' prop='typeSelect'>-->
          <!--<el-select-->
            <!--v-model='dynamicValidateForm.typeSelect'-->
            <!--placeholder='请选择'-->
          <!--&gt;-->
            <!--<el-option-->
              <!--v-for='item in options'-->
              <!--:key='item.value'-->
              <!--:label='item.label'-->
              <!--:value='item.value'-->
            <!--&gt;-->
            <!--</el-option>-->
          <!--</el-select>-->
        <!--</el-form-item>-->
        <!--<div-->
          <!--v-show='dynamicValidateForm.typeSelect === 0'-->
          <!--v-for='item in array'-->
          <!--:key='item.index'-->
        <!--&gt;-->
          <!--<el-form-item label='输入项'>-->
            <!--<input-->
              <!--type='text'-->
              <!--class='select_first input_common input_border_right'-->
              <!--readonly='true'-->
              <!--v-model='item.selectNumber'-->
            <!--/>-->
            <!--<input-->
              <!--type='text'-->
              <!--class='select_two input_common input_border_left'-->
              <!--readonly='true'-->
              <!--v-model='item.selectText'-->
            <!--/>-->
            <!--<span class='select_three input_common input_border_right'-->
            <!--&gt;扣分：</span-->
            <!--&gt;-->
            <!--<input-->
              <!--type='number'-->
              <!--class='select_four input_common input_border_left'-->
              <!--v-model='item.selectInput'-->
            <!--/>-->
          <!--</el-form-item>-->
        <!--</div>-->
        <div
          v-for='(item, index) in newarray'
          :key='index'
        >
          <el-form-item label='输入项'>
            <input
              type='text'
              class='select_first input_common input_border_right'
              readonly='true'
              v-model='item.selectNumber'
            />
            <input
              type='text'
              class='select_two input_common input_border_left'
              readonly='true'
              v-model='item.selectText'
            />
            <span class='select_three input_common input_border_right'
            >扣分：</span
            >
            <input
              type='number'
              class='select_four input_common input_border_left'
              v-model='item.selectInput'
            />
            <el-button
              type='danger'
              icon='el-icon-delete'
              circle
              @click='deleteData(item.index)'
            ></el-button>
            <el-button
              type='primary'
              icon='el-icon-edit'
              circle
              v-if='newarray.length - 1 === index'
              @click='add'
            ></el-button>
          </el-form-item>
        </div>
      </el-form>
      <span slot='footer' class='dialog-footer'>
        <el-button @click='centerDialogVisible = false'>取 消</el-button>
        <el-button type='primary' @click='submit'>确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  const TYPE = ['A.', 'B.', 'C.', 'D.', 'E.', 'F.']
  export default {
    name: 'profile',

    data() {
      return {
        centerDialogVisible: false,
        dynamicValidateForm: {
          typeSelect: ''
        },
        options: [
          {
            value: 0,
            label: '二选一'
          },
          {
            value: 1,
            label: '多选一'
          }
        ],
        array: [
          {
            selectNumber: 'A.',
            selectText: '是',
            selectInput: ''
          },
          {
            selectNumber: 'B.',
            selectText: '否',
            selectInput: ''
          }
        ],
        newarray: [
          {
            selectNumber: 'A.',
            selectText: '',
            selectInput: ''
          },
          {
            selectNumber: 'B.',
            selectText: '',
            selectInput: ''
          },
          {
            selectNumber: 'C.',
            selectText: '',
            selectInput: ''
          }
        ]
      }
    },
    created() {
      this.onchangeSelect()
    },
    methods: {
      // 赋值字母
      onchangeSelect() {
        this.newarray = this.newarray.map((item, index) => {
          item.selectNumber = TYPE[index]
          return item
        })
      },
      // 增加
      add() {
        if (this.newarray.length + 1 > 6) {
          this.$alert('最多输入6项')
          return
        } else {
          this.newarray.push({
            index: this.newarray.length,
            value: ''
          })
        }
        this.onchangeSelect()
      },
      // 删除
      deleteData(index) {
        if (this.newarray.length - 1 < 3) {
          this.$alert('至少输入3项')
          return
        } else {
          this.newarray.splice(index, 1)
        }
        this.onchangeSelect()
      },
      submit() {
        console.log(this.array)
        console.log(this.newarray)
      }
    }
  }
</script>

<style scoped>
  .profile {
    width: 100%;
    height: 100%;
    overflow: auto;
  }
  /* 每个input都有 */
  .input_common {
    border: 1px solid rgba(205, 205, 205, 1);
    border-radius: 2px;
  }

  /* 2,4 */
  .input_border_left {
    border-left: none;
    margin-left: -5px;
  }

  /* 1,3 */
  .input_border_right {
    border-right: none;
  }

  .select_first {
    width: 25px;
    height: 34px;
    padding-left: 10px;
  }
  .select_two {
    width: 55px;
    height: 33px;
    padding-bottom: 1px;
  }
  .select_three {
    padding: 7px 0px 8px 10px;
  }
  .select_four {
    width: 100px;
    height: 34px;
  }
</style>

