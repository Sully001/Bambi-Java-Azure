const ctx = document.getElementById('myChart');
Chart.defaults.color = '#000';

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Court Crystal Sneaker', 'Spike High Tops', 'Cloudbust Thunder Sneakers'],
        datasets: [{
            label: 'Top 3 Best Selling Shoes',
            data: [28, 10 , 5],
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
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            y: {
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
        responsive: true,
        maintainAspectRatio: false,
        scales: {
            x: {
                type: 'time',
                time: {
                    unit: 'day',
                }
            },
            y: {
                beginAtZero: true
            }
        }
    }
});



