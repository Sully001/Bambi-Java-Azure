const ctx = document.getElementById('myChart');
Chart.defaults.color = '#000';

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: shoes,
        datasets: [{
            label: 'Top 3 Best Selling Shoes',
            data: freqlist,
            backgroundColor: [
                'rgba(54, 162, 235, 0.2)',
                'rgba(153, 102, 255, 0.2)',
                'rgba(255, 159, 64, 0.2)',
            ],
            borderColor: [
                'rgba(54, 162, 235, 1)',
                'rgba(153, 102, 255, 1)',
                'rgba(255, 159, 64, 1)',
            ],
            borderWidth: 1
        }]
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: 'Description: Top Selling Shoes of Your Store This Year',
                font: {
                    weight: 'bold',
                }
            }
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Name Of Product',
                    font: {
                        weight: 'bold',
                    },
                },
            },
            y: {
                title: {
                    display: true,
                    text: 'Quantity of Each Sold',
                    font: {
                        weight: 'bold',
                    },
                },
                beginAtZero: true
            }
        }
    }
});

const revenueChart = document.getElementById('revenueChart');
Chart.defaults.color = '#000';

new Chart(revenueChart, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Trailing 7 Day Revenue (£GBP)',
            data: [
                {x: trailingSevenDays[6], y: dailyRevenue[6]},
                {x: trailingSevenDays[5], y: dailyRevenue[5]},
                {x: trailingSevenDays[4], y: dailyRevenue[4]},
                {x: trailingSevenDays[3], y: dailyRevenue[3]},
                {x: trailingSevenDays[2], y: dailyRevenue[2]},
                {x: trailingSevenDays[1], y: dailyRevenue[1]},
                {x: trailingSevenDays[0], y: dailyRevenue[0]},
            ],
            fill: true,
            backgroundColor: [
                'rgba(75, 192, 192, 0.2)',
            ],
            borderColor: [
                'rgba(75, 192, 192)',
            ],
            borderWidth: 1
        }]
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: 'Description: Revenue of the past 7 days',
                font: {
                    weight: 'bold',
                }
            }
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                  display: true,
                  text: 'Days',
                    font: {
                        weight: 'bold',
                    },
                },
                type: 'time',
                time: {
                    unit: 'day',
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Revenue (GBP)',
                    font: {
                        weight: 'bold',
                    },
                },
                beginAtZero: true
            }
        }
    }
});

//Preparing dataset
const monthlyDataset = [];

for (let i = 29; i >= 0; i--) {
    monthlyDataset.push({x: trailingThirtyDays[i], y: monthlyRevenue[i]})
}

const monthlyChart = document.getElementById('monthlyChart');
Chart.defaults.color = '#000';

new Chart(monthlyChart, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Trailing 30 Day Revenue (£GBP)',
            data: monthlyDataset,
            fill: true,
            backgroundColor: [
                'rgba(57, 61, 63, 0.2)',
            ],
            borderColor: [
                'rgba(57, 61, 63)',
            ],
            borderWidth: 1,
        }]
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: 'Description: Revenue of the past 30 days (Important for understanding trends)',
                font: {
                    weight: 'bold',
                }
            }
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                type: 'time',
                title: {
                    display: true,
                    text: 'Days',
                    font: {
                        weight: 'bold',
                    },
                },
                time: {
                    unit: 'day',
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Revenue (GBP)',
                    font: {
                        weight: 'bold',
                    },
                },
                beginAtZero: true
            }
        }
    }
});

const averageDailySpend = document.getElementById('avgDailySpend');
Chart.defaults.color = '#000';

new Chart(averageDailySpend, {
    type: 'line',
    data: {
        datasets: [{
            label: 'Trailing Average 7 Day Revenue (£GBP)',
            data: [
                {x: trailingSevenDays[6], y: avgDailySpend[6]},
                {x: trailingSevenDays[5], y: avgDailySpend[5]},
                {x: trailingSevenDays[4], y: avgDailySpend[4]},
                {x: trailingSevenDays[3], y: avgDailySpend[3]},
                {x: trailingSevenDays[2], y: avgDailySpend[2]},
                {x: trailingSevenDays[1], y: avgDailySpend[1]},
                {x: trailingSevenDays[0], y: avgDailySpend[0]},
            ],
            fill: true,
            backgroundColor: [
                'rgba(218, 86, 33, 0.2)',
            ],
            borderColor: [
                'rgba(218, 86, 33)',
            ],
            borderWidth: 1
        }]
    },
    options: {
        plugins: {
            title: {
                display: true,
                text: 'Description: Average Spend of a Customer in the Past 7 Days',
                font: {
                    weight: 'bold',
                }
            }
        },
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                title: {
                    display: true,
                    text: 'Days',
                    font: {
                        weight: 'bold',
                    },
                },
                type: 'time',
                time: {
                    unit: 'day',
                }
            },
            y: {
                title: {
                    display: true,
                    text: 'Revenue (GBP)',
                    font: {
                        weight: 'bold',
                    },
                },
                beginAtZero: true
            }
        }
    }
});



