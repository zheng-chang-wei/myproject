import Mock from 'mockjs'

export default [
  {
    url: '/data/root',
    type: 'post',
    response: config => {
      return {
        code: 0,
        data: [
          {path: 'D://test//data//2019年5月', name: '2019年5月', level: 'Month'},
          {path: 'D://test//data//2019年6月', name: '2019年6月', level: 'Month'},
          {path: 'D://test//data//2019年7月', name: '2019年7月', level: 'Month'},
          {path: 'D://test//data//2019年8月', name: '2019年8月', level: 'Month'}]
      }
    }
  },
  {
    url: '/data/list',
    type: 'post',
    response: config => {
      console.log(config.body)
      if (config.body.FileTreeNode.level == 0) {
        return {
          code: 0,
          data: [
            {path: 'D://test//data//2019年5月//1日', name: '1日', level: 'Date'},
            {path: 'D://test//data//2019年6月//2日', name: '2日', level: 'Date'},
            {path: 'D://test//data//2019年7月//3日', name: '3日', level: 'Date'},
            {path: 'D://test//data//2019年8月//4日', name: '4日', level: 'Date'},
            {path: 'D://test//data//2019年8月//5日', name: '5日', level: 'Date'},
            {path: 'D://test//data//2019年8月//6日', name: '6日', level: 'Date'}]
        }
      } else if (config.body.FileTreeNode.level == 1) {
        return {
          code: 0,
          data: [
            {path: 'http://localhost/video/111.mp4', name: '视频测试111', level: '2'},
            {path: 'http://localhost/video/222.mp4', name: '视频测试222', level: '2'},
            {path: 'D://test//data//2019年7月//3日//223', name: '223', level: '2'},
            {path: 'D://test//data//2019年8月//4日//42422', name: '42422', level: '2'},
            {path: 'D://test//data//2019年8月//5日//42422', name: '4222', level: '2'},
            {path: 'D://test//data//2019年8月//6日//42422', name: '422', level: '2'}]
        }
      }
    }
  },
  {
    url: '/data/query',
    type: 'post',
    response: config => {
      if (config.body.FileTreeNode == null) {
        return {
          code: 0,
          data: [
            {path: 'D://test//data//2019年5月', name: '2019年5月', level: 'Month'},
            {path: 'D://test//data//2019年6月', name: '2019年6月', level: 'Month'}]
        }
      } else {
        if (config.body.FileTreeNode.level == 0) {
          return {
            code: 0,
            data: [
              {path: 'D://test//data//2019年7月//3日', name: '3日', level: 'Date'},
              {path: 'D://test//data//2019年8月//4日', name: '4日', level: 'Date'}]
          }
        } else if (config.body.FileTreeNode.level == 1) {
          return {
            code: 0,
            data: [
              {path: 'D://test//data//2019年7月//3日//223', name: '223', level: 'Leaf'},
              {path: 'D://test//data//2019年8月//4日//42422', name: '42422', level: 'Leaf'}]
          }
        }
      }
    }
  },
  {
    url: '/data/header',
    type: 'post',
    response: config => {
      return {
        code: 0,
        data: {
          version: 1,
          headers: [
            {
              type: 'MVB', consumptionId: '1', slotId: '1', multicastIp: '127.0.0.1', multicastPort: '65535',
              variables: [
                {groupId: 1, signalName: '电压1', dataType: 7},
                {groupId: 1, signalName: '电流2', dataType: 7},
                {groupId: 1, signalName: '电流3', dataType: 7},
                {groupId: 1, signalName: '电流4', dataType: 7},
                {groupId: 1, signalName: '电压5', dataType: 7},
                {groupId: 1, signalName: '电流6', dataType: 7}
              ]
            },
            {
              type: 'TRDP', consumptionId: '1', slotId: '1', multicastIp: '127.0.0.1', multicastPort: '65535',
              variables: [
                {groupId: 1, signalName: '电压7', dataType: 7},
                {groupId: 1, signalName: '电流8', dataType: 7},
                {groupId: 1, signalName: '电流9', dataType: 7},
                {groupId: 1, signalName: '电流10', dataType: 7},
                {groupId: 1, signalName: '电压11', dataType: 7},
                {groupId: 1, signalName: '电流12', dataType: 7}
              ]
            }
          ]
        }
      }
    }
  }
]
