/*
   Licensed to the Apache Software Foundation (ASF) under one or more
   contributor license agreements.  See the NOTICE file distributed with
   this work for additional information regarding copyright ownership.
   The ASF licenses this file to You under the Apache License, Version 2.0
   (the "License"); you may not use this file except in compliance with
   the License.  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
var showControllersOnly = false;
var seriesFilter = "";
var filtersOnlySampleSeries = true;

/*
 * Add header in statistics table to group metrics by category
 * format
 *
 */
function summaryTableHeader(header) {
    var newRow = header.insertRow(-1);
    newRow.className = "tablesorter-no-sort";
    var cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Requests";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 3;
    cell.innerHTML = "Executions";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 7;
    cell.innerHTML = "Response Times (ms)";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 1;
    cell.innerHTML = "Throughput";
    newRow.appendChild(cell);

    cell = document.createElement('th');
    cell.setAttribute("data-sorter", false);
    cell.colSpan = 2;
    cell.innerHTML = "Network (KB/sec)";
    newRow.appendChild(cell);
}

/*
 * Populates the table identified by id parameter with the specified data and
 * format
 *
 */
function createTable(table, info, formatter, defaultSorts, seriesIndex, headerCreator) {
    var tableRef = table[0];

    // Create header and populate it with data.titles array
    var header = tableRef.createTHead();

    // Call callback is available
    if(headerCreator) {
        headerCreator(header);
    }

    var newRow = header.insertRow(-1);
    for (var index = 0; index < info.titles.length; index++) {
        var cell = document.createElement('th');
        cell.innerHTML = info.titles[index];
        newRow.appendChild(cell);
    }

    var tBody;

    // Create overall body if defined
    if(info.overall){
        tBody = document.createElement('tbody');
        tBody.className = "tablesorter-no-sort";
        tableRef.appendChild(tBody);
        var newRow = tBody.insertRow(-1);
        var data = info.overall.data;
        for(var index=0;index < data.length; index++){
            var cell = newRow.insertCell(-1);
            cell.innerHTML = formatter ? formatter(index, data[index]): data[index];
        }
    }

    // Create regular body
    tBody = document.createElement('tbody');
    tableRef.appendChild(tBody);

    var regexp;
    if(seriesFilter) {
        regexp = new RegExp(seriesFilter, 'i');
    }
    // Populate body with data.items array
    for(var index=0; index < info.items.length; index++){
        var item = info.items[index];
        if((!regexp || filtersOnlySampleSeries && !info.supportsControllersDiscrimination || regexp.test(item.data[seriesIndex]))
                &&
                (!showControllersOnly || !info.supportsControllersDiscrimination || item.isController)){
            if(item.data.length > 0) {
                var newRow = tBody.insertRow(-1);
                for(var col=0; col < item.data.length; col++){
                    var cell = newRow.insertCell(-1);
                    cell.innerHTML = formatter ? formatter(col, item.data[col]) : item.data[col];
                }
            }
        }
    }

    // Add support of columns sort
    table.tablesorter({sortList : defaultSorts});
}

$(document).ready(function() {

    // Customize table sorter default options
    $.extend( $.tablesorter.defaults, {
        theme: 'blue',
        cssInfoBlock: "tablesorter-no-sort",
        widthFixed: true,
        widgets: ['zebra']
    });

    var data = {"OkPercent": 57.142857142857146, "KoPercent": 42.857142857142854};
    var dataset = [
        {
            "label" : "KO",
            "data" : data.KoPercent,
            "color" : "#FF6347"
        },
        {
            "label" : "OK",
            "data" : data.OkPercent,
            "color" : "#9ACD32"
        }];
    $.plot($("#flot-requests-summary"), dataset, {
        series : {
            pie : {
                show : true,
                radius : 1,
                label : {
                    show : true,
                    radius : 3 / 4,
                    formatter : function(label, series) {
                        return '<div style="font-size:8pt;text-align:center;padding:2px;color:white;">'
                            + label
                            + '<br/>'
                            + Math.round10(series.percent, -2)
                            + '%</div>';
                    },
                    background : {
                        opacity : 0.5,
                        color : '#000'
                    }
                }
            }
        },
        legend : {
            show : true
        }
    });

    // Creates APDEX table
    createTable($("#apdexTable"), {"supportsControllersDiscrimination": true, "overall": {"data": [0.5238095238095238, 500, 1500, "Total"], "isController": false}, "titles": ["Apdex", "T (Toleration threshold)", "F (Frustration threshold)", "Label"], "items": [{"data": [1.0, 500, 1500, "\u83B7\u53D6\u7528\u4F8B\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6casetype"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u524D\u7F6E\u6761\u4EF6\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u6761\u4EF6\u72B6\u6001"], "isController": false}, {"data": [0.0, 500, 1500, "\u83B7\u53D6\u7528\u4F8Bsql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6case-api\u7684\u53C2\u6570\u7C7B\u578Bsql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u8BA1\u5212\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u7528\u4F8B\u6570\u636E\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6run\u7684\u53C2\u6570"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u53D1\u5E03\u5355\u5143\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6\u88AB\u6D4B\u670D\u52A1\u5668ip\u7ED3\u679Csql"], "isController": false}, {"data": [1.0, 500, 1500, "\u83B7\u53D6api\u7ED3\u679Csql"], "isController": false}, {"data": [0.0, 500, 1500, "HttpApiPerformance"], "isController": false}]}, function(index, item){
        switch(index){
            case 0:
                item = item.toFixed(3);
                break;
            case 1:
            case 2:
                item = formatDuration(item);
                break;
        }
        return item;
    }, [[0, 0]], 3);

    // Create statistics table
    createTable($("#statisticsTable"), {"supportsControllersDiscrimination": true, "overall": {"data": ["Total", 21, 9, 42.857142857142854, 623.7619047619048, 1, 3522, 124.0, 3180.4, 3493.3999999999996, 3522.0, 1.8941102191756112, 0.14647997091187878, 0.0], "isController": false}, "titles": ["Label", "#Samples", "KO", "Error %", "Average", "Min", "Max", "Median", "90th pct", "95th pct", "99th pct", "Transactions\/s", "Received", "Sent"], "items": [{"data": ["\u83B7\u53D6\u7528\u4F8B\u7ED3\u679Csql", 1, 0, 0.0, 2.0, 2, 2, 2.0, 2.0, 2.0, 2.0, 500.0, 194.82421875, 0.0], "isController": false}, {"data": ["\u83B7\u53D6casetype", 1, 0, 0.0, 156.0, 156, 156, 156.0, 156.0, 156.0, 156.0, 6.41025641025641, 0.0, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u524D\u7F6E\u6761\u4EF6\u7ED3\u679Csql", 1, 0, 0.0, 23.0, 23, 23, 23.0, 23.0, 23.0, 23.0, 43.47826086956522, 0.8916440217391305, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u6761\u4EF6\u72B6\u6001", 1, 0, 0.0, 27.0, 27, 27, 27.0, 27.0, 27.0, 27.0, 37.03703703703704, 0.3255208333333333, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u7528\u4F8Bsql", 1, 0, 0.0, 1786.0, 1786, 1786, 1786.0, 1786.0, 1786.0, 1786.0, 0.5599104143337066, 0.2181682180851064, 0.0], "isController": false}, {"data": ["\u83B7\u53D6case-api\u7684\u53C2\u6570\u7C7B\u578Bsql", 1, 0, 0.0, 3.0, 3, 3, 3.0, 3.0, 3.0, 3.0, 333.3333333333333, 6.510416666666667, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u8BA1\u5212\u7ED3\u679Csql", 1, 0, 0.0, 3.0, 3, 3, 3.0, 3.0, 3.0, 3.0, 333.3333333333333, 63.80208333333333, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u7528\u4F8B\u6570\u636E\u7ED3\u679Csql", 1, 0, 0.0, 3.0, 3, 3, 3.0, 3.0, 3.0, 3.0, 333.3333333333333, 133.13802083333334, 0.0], "isController": false}, {"data": ["\u83B7\u53D6run\u7684\u53C2\u6570", 1, 0, 0.0, 483.0, 483, 483, 483.0, 483.0, 483.0, 483.0, 2.070393374741201, 0.0, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u53D1\u5E03\u5355\u5143\u7ED3\u679Csql", 1, 0, 0.0, 5.0, 5, 5, 5.0, 5.0, 5.0, 5.0, 200.0, 4.4921875, 0.0], "isController": false}, {"data": ["\u83B7\u53D6\u88AB\u6D4B\u670D\u52A1\u5668ip\u7ED3\u679Csql", 1, 0, 0.0, 1.0, 1, 1, 1.0, 1.0, 1.0, 1.0, 1000.0, 63.4765625, 0.0], "isController": false}, {"data": ["\u83B7\u53D6api\u7ED3\u679Csql", 1, 0, 0.0, 2.0, 2, 2, 2.0, 2.0, 2.0, 2.0, 500.0, 59.5703125, 0.0], "isController": false}, {"data": ["HttpApiPerformance", 9, 9, 100.0, 1178.3333333333333, 121, 3522, 171.0, 3522.0, 3522.0, 3522.0, 2.288911495422177, 0.0, 0.0], "isController": false}]}, function(index, item){
        switch(index){
            // Errors pct
            case 3:
                item = item.toFixed(2) + '%';
                break;
            // Mean
            case 4:
            // Mean
            case 7:
            // Median
            case 8:
            // Percentile 1
            case 9:
            // Percentile 2
            case 10:
            // Percentile 3
            case 11:
            // Throughput
            case 12:
            // Kbytes/s
            case 13:
            // Sent Kbytes/s
                item = item.toFixed(2);
                break;
        }
        return item;
    }, [[0, 0]], 0, summaryTableHeader);

    // Create error table
    createTable($("#errorsTable"), {"supportsControllersDiscrimination": false, "titles": ["Type of error", "Number of errors", "% in errors", "% in all samples"], "items": [{"data": ["", 9, 100.0, 42.857142857142854], "isController": false}]}, function(index, item){
        switch(index){
            case 2:
            case 3:
                item = item.toFixed(2) + '%';
                break;
        }
        return item;
    }, [[1, 1]]);

        // Create top5 errors by sampler
    createTable($("#top5ErrorsBySamplerTable"), {"supportsControllersDiscrimination": false, "overall": {"data": ["Total", 21, 9, "", 9, null, null, null, null, null, null, null, null], "isController": false}, "titles": ["Sample", "#Samples", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors", "Error", "#Errors"], "items": [{"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": [], "isController": false}, {"data": ["HttpApiPerformance", 9, 9, "", 9, null, null, null, null, null, null, null, null], "isController": false}]}, function(index, item){
        return item;
    }, [[0, 0]], 0);

});
