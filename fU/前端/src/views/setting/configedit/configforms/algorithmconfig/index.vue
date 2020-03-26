<template>
  <div>
    <el-button type="primary" style="margin-bottom:10px; margin-top:10px" @click="dialogFormVisible = true">添加算法</el-button>
    <el-dialog title="输入算法名称" :visible.sync="dialogFormVisible">
      <el-form>
        <el-form-item label="算法名称">
          <el-input v-model="algorithmElmName" placeholder="请输入算法:(格式：XXX算法)" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="confirm()">确 定</el-button>
      </div>
    </el-dialog>
    <div style="min-height: 200px; margin-bottom: 50px">
      <template v-for="(item,index) in algorithmSettings.algorithms">
        <algorithm
          :key="index"
          :algorithm-elm="item"
          :variables="algorithmSettings.variables"
          :subsystems="subsystems"
          @deleteBoard="deleteBoard"
        />
      </template>
    </div>
    <div style="position: absolute; bottom: 10px">
      <el-button style="width: 210px" @click="cancel">取消</el-button>
      <el-button style="width: 210px" type="primary" @click="previousStep">上一步</el-button>
      <el-button style="width: 210px" type="primary" @click="nextStep">下一步</el-button>
    </div>
  </div>
</template>

<script>
import algorithm from './algorithm'
import app from '@/common/js/app'
export default {
  components: {
    algorithm
  },
  props: {
    algorithmSettings: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      dialogFormVisible: false,
      algorithmElmName: '',
      subsystems: []
    }
  },
  mounted() {
    this.selectAllSubsystems()
  },
  methods: {
    selectAllSubsystems() {
      app.get('select_all_subsystems').then(data => {
        if (data.code === 0) {
          this.subsystems = data.data
        }
      })
    },
    cancel() {
      this.$emit('cancel')
    },
    previousStep() {
      this.$emit('previousStep')
    },
    nextStep() {
      this.$emit('nextStep')
    },
    confirm() {
      const algorithmElm = {
        name: this.algorithmElmName,
        slotId: '',
        fileOriginalName: '',
        enable: true,
        mvbGroups: [],
        ecnGroups: [],
        adGroups: []
      }
      this.dialogFormVisible = false
      this.algorithmSettings.algorithms.push(algorithmElm)
    },
    deleteBoard(index) {
      this.algorithms.splice(index, 1)
    }

  }
}
</script>

<style>

</style>
