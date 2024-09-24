## <aside>🏁 **Goal:"Spart Delivery 아웃소싱 프로젝트"**</aside>
## ⚜API명세서 

## 회원가입 로그인 비밀번호 변경 탈퇴 (노현지)
<img width="1171" alt="스크린샷 2024-09-24 오후 7 49 52" src="https://github.com/user-attachments/assets/6911682b-e7f0-49a7-9a6e-754673587710">

## 가게 생성 수정 조회 페업 (오현택)
<img width="1165" alt="스크린샷 2024-09-24 오후 7 50 34" src="https://github.com/user-attachments/assets/14b6de77-f85b-4718-8d10-00a90b593e06">

## 메뉴 수정 삭제 및 메뉴 카테고리 (이재희)
<img width="1165" alt="스크린샷 2024-09-24 오후 7 50 34" src="https://github.com/user-attachments/assets/5e97da38-c3cd-467b-be60-30300021fe13">

## 주문 음식주문 및 배달 상태 확인 (김나영)
<img width="1157" alt="스크린샷 2024-09-24 오후 8 27 03" src="https://github.com/user-attachments/assets/0746daa3-3071-4f10-a6cf-70389f24e949">

## ⚜ERD
![image](https://github.com/user-attachments/assets/e8a2a5a0-8747-4595-a1c9-84d18e016c34)
# Sparta Delivery API Documentation

## 📌 **1. 회원가입**

회원가입 API는 새로운 계정을 생성하고 JWT 토큰을 발급합니다.

- **이메일 중복 검사**: 이미 등록된 이메일이면 예외 발생.
- **사용자 이름 중복 검사**: 이미 등록된 사용자 이름이면 예외 발생.
- **비밀번호 암호화**: 안전하게 암호화 후 저장.
- **유저 역할 설정**: 입력된 역할에 맞는 권한 부여.

**예외 처리**:  
`EmailAlreadyExistsException`, `UsernameAlreadyExistsException`, `InvalidUserRoleException`

---

## 🔑 **2. 로그인**

로그인 API는 인증 후 JWT 토큰을 발급합니다.

- **이메일 확인**: 등록된 유저인지 확인.
- **비밀번호 확인**: 비밀번호 일치 여부 확인.

**예외 처리**:  
`UserNotRegisteredException`, `AuthInvalidPasswordException`

---

## 🔄 **3. 비밀번호 변경**

비밀번호 변경 API는 기존 비밀번호 확인 후 새로운 비밀번호로 변경합니다.

- **기존 비밀번호 확인**: 입력된 비밀번호가 일치하는지 확인.
- **새 비밀번호 확인**: 기존 비밀번호와 다른지 확인 후 변경.

**예외 처리**:  
`UserNotFoundException`, `UserInvalidPasswordException`, `SamePasswordException`

---

## ❌ **4. 회원 탈퇴**

회원 탈퇴 API는 비밀번호 확인 후 계정을 삭제 처리합니다.

- **비밀번호 확인**: 저장된 비밀번호와 일치하는지 확인.
- **탈퇴 처리**: 탈퇴 상태로 변경 후 저장.

**예외 처리**:  
`UserNotFoundException`, `UserInvalidPasswordException`, `AlreadyDeletedUserException`

---

## 🏪 **5. 가게 관리**

### ➡️ **가게 등록**

사장님 권한으로 가게를 생성합니다.

- **사장님 권한 확인**: 권한이 없으면 예외 발생.
- **가게 이름 중복 확인**: 이름 중복이면 예외 발생.
- **가게 최대 생성 제한**: 최대 3개까지 가게 생성 가능.

**예외 처리**:  
`PermissionDefinedOwnerException`, `StoreNameIsExitsException`

### ✏️ **가게 수정**

사장님은 가게 정보를 수정할 수 있습니다.

- **가게 이름 중복 확인**: 다른 가게와 이름 중복되지 않도록 확인.
- **권한 확인**: 가게 소유자만 수정 가능.

**예외 처리**:  
`PermissionDefinedStoreUpdateException`

### 🔍 **가게 검색 및 조회**

특정 가게 이름으로 가게 목록을 검색하고, 상세 정보를 조회합니다.

- **가게 이름 검색**: 이름으로 가게 목록을 조회.
- **가게 상세 정보 조회**: 가게 ID로 세부 정보를 확인.

---

## 📊 **6. 가게 통계 조회**

사장님이 운영하는 가게의 매출 및 주문 통계를 조회합니다.

- **권한 확인**: 사장님만 통계 데이터를 조회할 수 있습니다.
- **날짜 형식 확인**: 일별 및 월별로 통계 조회 가능.

**예외 처리**:  
`PermissionDefinedOwnerException`, `DateFormatException`

---

## 🍽️ **7. 메뉴 관리**

### ➕ **메뉴 생성**

사장님 권한으로 새로운 메뉴를 등록합니다.

- **메뉴 중복 확인**: 같은 가게에 이미 등록된 메뉴가 있는지 확인.

**예외 처리**:  
`IllegalArgumentException`

### ✏️ **메뉴 수정**

사장님은 메뉴 정보를 수정할 수 있습니다.

- **메뉴 존재 여부 확인**: 메뉴가 존재하는지 확인 후 수정.

**예외 처리**:  
`NullPointerException`

### ❌ **메뉴 삭제**

사장님은 메뉴를 삭제할 수 있습니다.

- **권한 검증**: 사장님 권한 확인 후 삭제 처리.

**예외 처리**:  
`IllegalStateException`, `NullPointerException`

---

## 📦 **8. 주문 관리**

### 🛒 **주문 생성**

사용자가 선택한 메뉴를 주문합니다.

- **메뉴 및 가게 검증**: 메뉴와 가게의 유효성 확인.
- **최소 주문 금액 확인**: 최소 주문 금액 이상인지 확인.

**예외 처리**:  
`NullPointerException`, `IllegalAccessException`

### 📜 **주문 내역 조회 (사장님만)**

사장님은 모든 주문 내역을 조회할 수 있습니다.

- **사장님 권한 확인**: 사장님 권한을 가진 사용자만 조회 가능.

**예외 처리**:  
`IllegalAccessException`, `NullPointerException`

### 🔄 **주문 상태 변경 (사장님만)**

사장님은 주문의 상태를 변경할 수 있습니다.

- **주문 상태 변경**: '주문 수락' 또는 '배달 완료' 상태로 변경.

**예외 처리**:  
`IllegalAccessException`, `NullPointerException`

---

## 🔑 **사장님 권한 검증**

사장님 권한이 있는 사용자인지 확인합니다.

**예외 처리**:  
`IllegalAccessException`
