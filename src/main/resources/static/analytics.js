const ctx = document.getElementById('myChart');
Chart.defaults.color = '#000';

new Chart(ctx, {
    type: 'bar',
    data: {
        labels: ['Court Crystal Sneaker', 'Spike High Tops', 'Cloudbust Thunder Sneakers'],
        datasets: [{
            label: '# of Pairs Sold',
            data: [28, 10 , 5],
            backgroundColor: [
                'rgba(255, 99, 132, 0.7)',
                'rgba(54, 162, 235, 0,7)',
                'rgba(255, 286, 86, 0.7)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 0.7)',
                'rgba(54, 162, 235, 0.7)',
                'rgba(255, 286, 86, 0.7)',
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
    type: 'bar',
    data: {
        datasets: [{
            label: 'Revenue (£GBP)',
            data: [
                {x: trailingSevenDays[6], y: dailyRevenue[6]},
                {x: trailingSevenDays[5], y: dailyRevenue[5]},
                {x: trailingSevenDays[4], y: dailyRevenue[4]},
                {x: trailingSevenDays[3], y: dailyRevenue[3]},
                {x: trailingSevenDays[2], y: dailyRevenue[2]},
                {x: trailingSevenDays[1], y: dailyRevenue[1]},
                {x: trailingSevenDays[0], y: dailyRevenue[0]},
            ],
            backgroundColor: [
                'rgba(255, 99, 132, 0.3)',
                'rgba(54, 162, 235, 0,3)',
                'rgba(255, 286, 86, 0.3)',
                'rgba(255, 99, 132, 0.3)',
                'rgba(54, 162, 235, 0,3)',
                'rgba(255, 286, 86, 0.3)',
            ],
            borderColor: [
                'rgba(255, 99, 132, 0.3)',
                'rgba(54, 162, 235, 0.3)',
                'rgba(255, 286, 86, 0.3)',
                'rgba(255, 99, 132, 0.3)',
                'rgba(54, 162, 235, 0.3)',
                'rgba(255, 286, 86, 0.3)',
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



