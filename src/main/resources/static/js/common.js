// 숫자 인지 아닌지 체크하는 함수
function isNumeric(val) {
  return !isNaN(val) && !isNan(parseFloat(val));
}

// 텍스트의 byte 수를 반환하는 함수
function getBytesSize(str) {
  const encoder = new TextEncoder();
  const byteArray = encoder.encode(str);
  return byteArray.length;
}

export { getBytesSize };
