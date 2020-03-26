<template>
  <div class="ad">
    <el-collapse>
      <el-collapse-item :title="adForm.type.replace('CARD_TYPE_','')">
        <el-form ref="adForm" v-model="adForm" label-position="left" label-width="100px">
          <el-form-item prop="delivery" style="text-align: right">
            <el-switch v-model="adForm.enable" style="margin-right: 30px" />
            <el-button type="danger" icon="el-icon-close" size="mini" circle @click="deleteBoard()" />
          </el-form-item>
          <el-form-item label="卡槽号">
            <el-input v-model="adForm.slotId" style="width:300px" />
          </el-form-item>
          <el-form-item label="前面板IP">
            <el-input v-model="adForm.ip" style="width:300px" />
          </el-form-item>
          <el-button type="primary" size="small" @click="addListRow()">添加变量</el-button>
          <el-table
            :data="adForm.variables"
            style="margin-top:20px; width: 100%"
          >
            <el-table-column prop="chnId" label="通道号">
              <template slot-scope="scope">
                <el-input v-model="scope.row.chnId" placeholder="通道号" />
              </template>
            </el-table-column>
            <el-table-column prop="sampleRate" label="采样率">
              <template slot-scope="scope">
                <el-input v-model="scope.row.sampleRate" placeholder="采样率" />
              </template>
            </el-table-column>
            <el-table-column prop="name" label="变量名">
              <template slot-scope="scope">
                <el-input v-model="scope.row.name" placeholder="变量名" />
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button
                  v-if="!scope.row.editing"
                  size="mini"
                  type="danger"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.$index, scope.row)"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </el-collapse-item>
    </el-collapse>
  </div>
</template>

<script>
export default {
  props: {
    index: {
      type: Number,
      default: null
    },
    adForm: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      listStructure: [
        { prop: 'chnId', propName: '通道号' },
        { prop: 'sampleRate', propName: '采样率' },
        { prop: 'name', propName: '变量名' }
      ]
    }
  },
  mounted() {
  },
  methods: {
    deleteBoard() {
      this.$emit('deleteBoard', this.index)
    },
    addListRow() {
      const oneRow = {
        chnId: '',
        sampleRate: '',
        name: ''
      }
      this.adForm.variables.push(oneRow)
    },
    handleDelete(index, rows) {
      this.adForm.variables.splice(index, 1)
    }
  }
}
</script>

<style>

</style>
