const btnUpdateEle = document.getElementById('btnUpdate')
const btnDeleteEle = document.getElementById('btnDelete')
const btnFindAllEle = document.getElementById('btnFindAll')

// 수정
btnUpdateEle.addEventListener('click',e=>{
  const productIdEle = document.getElementById('productId')
  location.href = `/products/${productIdEle.value}/edit`;
},false)

//삭제
btnDeleteEle.addEventListener('click',e=>{
  if(!confirm('삭제하시겠습니까?')) return;

  const productIdEle = document.getElementById('productId')
  location.href = `/products/${productIdEle.value}/del`;
})

//목록
btnFindAllEle.addEventListener('click',e=>{
  location.href = `/products`;
})