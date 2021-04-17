<template>
  <div :id="id" :class="className" :style="{height:height,width:width}"/>
</template>

<script>
  import echarts from 'echarts'
  import resize from './mixins/resizeline'
//  import { getapireportstaticsList as getapireportstaticsList } from '@/api/reportcenter/apireportstatics'

  export default {
    mixins: [resize],
    props: {
      className: {
        type: String,
        default: 'chart'
      },
      LineName: {
        type: String,
        default: 'chart'
      },
      id: {
        type: String,
        default: 'chart'
      },
      width: {
        type: String,
        default: '200px'
      },
      height: {
        type: String,
        default: '200px'
      },
      StaticsData: {
        type: Array,
        required: true
      },
      PlanDateData: {
        type: Array,
        required: true
      }
    },

    watch: {
      // 监听的变量名
      StaticsData(val) {
        this.StaticsData = val
        this.getStatics()
        this.initChart()
      },
      PlanDateData(val) {
        console.error(val)
        this.PlanDateData = val
        this.initChart()
      }
    },

    data() {
      return {
        chart: null,
        PlanStatics: [], // 返回计划统计结果
        PlanList: []
      }
    },
    mounted() {
      this.getStatics()
      this.initChart()
    },
    beforeDestroy() {
      if (!this.chart) {
        return
      }
      this.chart.dispose()
      this.chart = null
    },
    methods: {
      getStatics() {
        console.log('this.StaticsData...................')
        console.log(this.StaticsData)
        for (let i = 0; i < this.StaticsData.length; i++) {
          var LineObject = {
            name: this.StaticsData[i].execPlanName,
            type: 'line',
            smooth: true,
            symbol: 'circle',
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1
              }
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(1, 189, 27, 0.3)'
                }, {
                  offset: 0.8,
                  color: 'rgba(1, 189, 27, 0)'
                }], false),
                shadowColor: 'rgba(0, 0, 0, 0.1)',
                shadowBlur: 10
              }
            },
            itemStyle: {
              normal: {
                color: 'rgb(300,189,27)',
                borderColor: 'rgba(300,189,2,0.27)',
                borderWidth: 12

              }
            },
            data: this.StaticsData[i].passPecent
          }
          this.PlanList.push(LineObject)
        }
        // this.initChart()
        console.log('PlanList11111111111111111111111111111111111111111111111111PlanList')
        console.log(this.PlanList)
      },

      initChart() {
        console.log('000000000000000000000000000000000000000000000000000')
        this.chart = echarts.init(document.getElementById(this.id))
        this.chart.setOption({
          backgroundColor: '#08060a',
          title: {
            top: 20,
            text: this.LineName,
            textStyle: {
              fontWeight: 'normal',
              fontSize: 16,
              color: '#F1F1F3'
            },
            left: '1%'
          },
          tooltip: {
            trigger: 'axis',
            axisPointer: {
              lineStyle: {
                color: '#57617B'
              }
            }
          },
          legend: {
            top: 20,
            icon: 'rect',
            itemWidth: 14,
            itemHeight: 5,
            itemGap: 13,
            data: this.PlanList,
            right: '4%',
            textStyle: {
              fontSize: 12,
              color: '#F1F1F3'
            }
          },
          grid: {
            top: 100,
            left: '2%',
            right: '2%',
            bottom: '2%',
            containLabel: true
          },
          xAxis: [{
            type: 'category',
            boundaryGap: false,
            axisLine: {
              lineStyle: {
                color: '#57617B'
              }
            },
            data: this.PlanDateData
          }],
          yAxis: [{
            type: 'value',
            name: '(%)',
            axisTick: {
              show: false
            },
            axisLine: {
              lineStyle: {
                color: '#57617B'
              }
            },
            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14
              }
            },
            splitLine: {
              lineStyle: {
                color: '#57617B'
              }
            }
          }],
          series: this.PlanList
        })
      }
    }
  }
</script>
