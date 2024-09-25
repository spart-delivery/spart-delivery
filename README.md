# 🛠️ **Sparta Delivery API Documentation**

## 🏁 **프로젝트 목표**

> **"Sparta Delivery 아웃소싱 프로젝트"**  
> 이 문서는 Sparta Delivery 프로젝트의 API 명세서, 주요 기능 및 트러블슈팅 내용을 다룹니다. 프로젝트의 목표는 효율적이고 사용자 친화적인 배달 서비스 플랫폼을 제공하는 것입니다.

---

## ⚜ **API 명세서**

### **1. 회원가입, 로그인, 비밀번호 변경, 탈퇴** (노현지)
<img width="1171" alt="회원가입 로그인 비밀번호 변경 탈퇴" src="https://github.com/user-attachments/assets/6911682b-e7f0-49a7-9a6e-754673587710">

### **2. 가게 생성, 수정, 조회, 폐업** (오현택)
<img width="1165" alt="가게 생성 수정 조회 폐업" src="https://github.com/user-attachments/assets/14b6de77-f85b-4718-8d10-00a90b593e06">

### **3. 메뉴 수정, 삭제, 카테고리 관리** (이재희)
<img width="1165" alt="메뉴 수정 삭제 카테고리" src="https://github.com/user-attachments/assets/5e97da38-c3cd-467b-be60-30300021fe13">

### **4. 주문 생성, 배달 상태 확인** (김나영)
<img width="1157" alt="주문 음식주문 배달 상태" src="https://github.com/user-attachments/assets/0746daa3-3071-4f10-a6cf-70389f24e949">

---

## ⚜ **ERD (Entity-Relationship Diagram)**

> **ERD 구조 다이어그램**  
> Sparta Delivery의 핵심 데이터베이스 구조를 나타내는 ERD입니다. 각 테이블 간의 관계와 주요 엔티티를 시각적으로 표현했습니다.
![ERD](https://github.com/user-attachments/assets/e8a2a5a0-8747-4595-a1c9-84d18e016c34)

---
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


## 🚀 **주요 기능 및 구현**

### 1. **JWT 토큰 사용자 인증**

- **기능**:  
  사용자 ID, 이메일, 역할 정보를 바탕으로 JWT 토큰을 생성하여 인증합니다.
  
- **설명**:
    - `createToken(Long userId, String email, UserRole userRole)`: 사용자 정보를 기반으로 JWT 토큰을 생성합니다.
    - `substringToken(String tokenValue)`: Bearer 접두어를 제거한 후 토큰을 추출합니다.
    - `extractClaims(String token)`: 토큰에서 클레임 정보를 추출합니다.

---

### 2. **카카오톡 로그인**

- **기능**:  
  카카오 계정을 통한 소셜 로그인 기능을 제공합니다.

- **설명**:  
  카카오 인가코드를 통해 유저 정보를 받아 로그인 또는 회원가입이 가능합니다. 카카오 API 요청을 통해 사용자 정보를 얻고, 해당 정보를 바탕으로 JWT 토큰을 생성합니다.

---

### 3. **Response 통일**

- **기능**:  
  동일한 Response 객체를 사용하여 API 응답의 일관성을 유지합니다.

- **설명**:  
  모든 API 응답을 동일한 형식의 Response 객체로 반환하여 통일성을 유지하고, 일관된 응답 구조를 보장합니다.

---

### 4. **GlobalException (다형성 적용)**

- **기능**:  
  커스텀 예외를 `CommonException` 클래스로 통합 관리합니다.

- **설명**:  
  커스텀 예외는 `CommonException`을 상속받아 처리되며, 모든 예외는 `GlobalException` 내의 `CommonExceptionHandler`에서 통합적으로 처리됩니다.

---

### 5. **AOP 적용**

- **기능**:  
  주문 요청 및 상태 변경 시 AOP를 기반으로 로그를 남깁니다.

- **설명**:  
  손님의 주문 요청과 사장님의 주문 상태 변경(대기 → 수락, 수락 → 배달 완료) 시 AOP를 적용해 로그를 남기는 로직을 구현했습니다.

---

### 6. **객체 단일 책임 원칙 (SRP)**

- **기능**:  
  엔티티 간의 연관 관계를 맺지 않고, 각자의 테이블만 관리하도록 설정했습니다.

- **설명**:  
  엔티티는 단일 책임 원칙을 준수하며, 각 테이블은 독립적으로 관리됩니다.

---

## 🛠️ **트러블슈팅**

### 1. **시간 처리 문제**

- **문제**:  
  클라이언트에서 시간을 `String` 타입으로 전달하지만, `requestDto`에서 `LocalTime` 타입으로 처리해 시간이 조금만 틀려도 오류가 발생했습니다.

- **해결**:  
  `requestDto`의 시간을 `String` 타입으로 변경한 후, 서비스 레이어에서 `LocalTime`으로 변환하여 처리했습니다. 이를 통해 클라이언트에서 잘못된 시간 형식으로 인해 발생하는 오류를 방지했습니다.

---

### 2. **로그 출력 문제**

- **문제**:  
  `@After` 어노테이션만으로는 HTTP 메서드의 반환값을 로그에서 확인할 수 없어, 주문 요청 이후 생성되는 주문 ID를 출력할 수 없었습니다.

- **해결**:  
  `@AfterReturning` 어노테이션으로 변경해, 주문 완료 후 반환되는 주문 ID를 로그에 출력하도록 개선했습니다. 이를 통해 주문 처리 흐름을 명확하게 파악할 수 있게 되었습니다.

---

### 3. **카카오 로그인 문제**

- **문제**:  
  카카오 로그인 구현 시, 토큰이 응답되지 않는 문제가 발생했습니다. 이는 `redirect_uri`가 요청된 URI와 일치하지 않아 발생한 문제였습니다.

- **해결**:  
  `redirect_uri`를 요청 URI와 통일시켜 문제를 해결했습니다. 이제 카카오 로그인에서 정상적으로 토큰이 응답됩니다.

---

## 📋 **기타 설명**

> **Sparta Delivery 프로젝트**는 배달 서비스 플랫폼으로, 효율적인 주문 관리와 사용자 인증을 제공합니다.  
> JWT 토큰을 통한 인증과 카카오 소셜 로그인, AOP 기반 로그 관리, 일관된 API 응답 처리 등 다양한 기능을 포함하고 있습니다.

---

## 🔗 **참조**

- **카카오톡 로그인 API**: [카카오 API 문서](https://developers.kakao.com/docs/latest/ko/kakaologin/common)
- **JWT**: [JWT.io](https://jwt.io/)
