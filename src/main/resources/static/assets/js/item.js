document.addEventListener("DOMContentLoaded", function () {
    const cardListContainer = document.getElementById("card-list");

    axios.get('/api/items')
        .then(function (response) {
            // 解析 R<T> 结构的响应体
            const responseData = response.data;

            // 检查响应的状态码
            if (responseData.code === 1) {
                // 获取 data 字段中的列表数据
                const list = responseData.data;

                // 遍历每个 item，动态生成 HTML 卡片
                list.forEach(item => {
                    const cardHtml = `
                        <div class="col d-flex justify-content-center">
                            <div class="card mb-3 mt-3" style="width: 800px">
                                <div class="row g-0">
                                    <!-- 左侧的文本内容 -->
                                    <div class="col-md-8">
                                        <div class="card-body">
                                            <h5 class="card-title">
                                                <a href="/item/detail/${item.sid}" class="titlelink">${item.title}</a>
                                            </h5>
                                            <p class="card-text">
                                                <small class="text-muted">Description: ${item.description}</small>
                                            </p>
                                            <p class="card-text">Category: ${item.category}</p>
                                            <p class="card-text">Publisher: ${item.contributor}</p>
                                            <p class="card-text">
                                                <small class="text-muted">${item.publish_date}</small>
                                            </p>
                                        </div>
                                    </div>
                                    <!-- 右侧的图片内容 -->
                                    <div class="col-md-4">
                                        <div class="image-container">
                                            ${item.file_format.toLowerCase() === 'jpg' || item.file_format.toLowerCase() === 'png' ?
                        `<img src="${item.file_path}" class="img-fluid img-uniform-size" alt="list_image" />` :
                        `<img src="/static/assets/images/documentImage.png" class="img-fluid img-uniform-size" alt="list_image" />`}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    `;
                    // 将生成的 HTML 插入到容器中
                    cardListContainer.insertAdjacentHTML('beforeend', cardHtml);
                });
            } else {
                // 如果 code 不为 1，表示请求失败，显示错误信息
                console.error('Error fetching items:', responseData.msg);
                alert('Failed to load items: ' + responseData.msg);
            }
        })
        .catch(function (error) {
            console.error('Error fetching items:', error);
            alert('An error occurred while loading items.');
        });
});
