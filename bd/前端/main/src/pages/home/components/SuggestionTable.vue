<!-- 处理建议表格 -->
<template>
  <div class="detail-body">
    <el-row>
      <el-col :span="6">
        <div class="detail-title detail-cell">项目</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">{{ select.project }}</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-title detail-cell">车辆号</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">{{ select.train }}</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-title detail-cell">车厢号</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">{{ select.carriage }}车</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-title detail-cell">门地址</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">{{ select.door }}门</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-title detail-cell">{{ item }}名称</div>
      </el-col>
      <el-col :span="18">
        <div class="detail-cell">{{ select.faultMode }}</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-title detail-cell">发生时间</div>
      </el-col>
      <el-col :span="18">
        <div class="detail-cell">{{ select.faultTime }}</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-title detail-cell">所属系统</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">车门系统</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-title detail-cell">{{ item }}来源</div>
      </el-col>
      <el-col :span="6">
        <div class="detail-cell">博得</div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-content detail-title detail-cell">处理建议</div>
      </el-col>
      <el-col :span="18">
        <div class="detail-content detail-cell">
          {{ select.treatment }}
        </div>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="6">
        <div class="detail-content detail-title detail-cell">维修建议</div>
      </el-col>
      <el-col :span="18">
        <div class="detail-content detail-cell">
          {{ select.repair }}
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
export default {
  props: {
    item: {
      type: String,
      default: null
    },
    select: {
      type: Object,
      default: null
    }
  }
}

</script>
<style>
  .detail-cell {
    padding: 5px;
    border: 1px solid #B0C4DE;
  }

  .detail-title {
    text-align: right;
    color: #00CCCC;
    font-weight: bold;
  }

  .detail-content {
    height: 100px;
  }

  pre {
    font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei, Arial, sans-serif;
  }

</style>
