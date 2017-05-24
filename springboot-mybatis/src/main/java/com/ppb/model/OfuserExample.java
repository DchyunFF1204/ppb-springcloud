package com.ppb.model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * <br>
 * <b>功能：</b>OfuserExample 参数类<br>
 * <b>作者：</b>daizy<br>
 * <b>日期：</b> 2017-05-24 14:45:34 <br>
 */
public class OfuserExample  {
	
	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;
    
    public OfuserExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }
        
		public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }
        
         public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }
        
         public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }
        
         public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }
        
        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }
        
         public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }
		public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }
        
         public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }
        
         public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }
        
         public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }
        
        public Criteria andUsernameGreaterThan(Long value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }
        
         public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }
		public Criteria andPlainpasswordIsNull() {
            addCriterion("plainPassword is null");
            return (Criteria) this;
        }
        
         public Criteria andPlainpasswordIsNotNull() {
            addCriterion("plainPassword is not null");
            return (Criteria) this;
        }
        
         public Criteria andPlainpasswordEqualTo(String value) {
            addCriterion("plainPassword =", value, "plainpassword");
            return (Criteria) this;
        }
        
         public Criteria andPlainpasswordNotEqualTo(String value) {
            addCriterion("plainPassword <>", value, "plainpassword");
            return (Criteria) this;
        }
        
        public Criteria andPlainpasswordGreaterThan(Long value) {
            addCriterion("plainPassword >", value, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("plainPassword >=", value, "plainpassword");
            return (Criteria) this;
        }
        
         public Criteria andPlainpasswordLessThan(String value) {
            addCriterion("plainPassword <", value, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordLessThanOrEqualTo(String value) {
            addCriterion("plainPassword <=", value, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordIn(List<String> values) {
            addCriterion("plainPassword in", values, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordNotIn(List<String> values) {
            addCriterion("plainPassword not in", values, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordBetween(String value1, String value2) {
            addCriterion("plainPassword between", value1, value2, "plainpassword");
            return (Criteria) this;
        }

        public Criteria andPlainpasswordNotBetween(String value1, String value2) {
            addCriterion("plainPassword not between", value1, value2, "plainpassword");
            return (Criteria) this;
        }
		public Criteria andEncryptedpasswordIsNull() {
            addCriterion("encryptedPassword is null");
            return (Criteria) this;
        }
        
         public Criteria andEncryptedpasswordIsNotNull() {
            addCriterion("encryptedPassword is not null");
            return (Criteria) this;
        }
        
         public Criteria andEncryptedpasswordEqualTo(String value) {
            addCriterion("encryptedPassword =", value, "encryptedpassword");
            return (Criteria) this;
        }
        
         public Criteria andEncryptedpasswordNotEqualTo(String value) {
            addCriterion("encryptedPassword <>", value, "encryptedpassword");
            return (Criteria) this;
        }
        
        public Criteria andEncryptedpasswordGreaterThan(Long value) {
            addCriterion("encryptedPassword >", value, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordGreaterThanOrEqualTo(String value) {
            addCriterion("encryptedPassword >=", value, "encryptedpassword");
            return (Criteria) this;
        }
        
         public Criteria andEncryptedpasswordLessThan(String value) {
            addCriterion("encryptedPassword <", value, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordLessThanOrEqualTo(String value) {
            addCriterion("encryptedPassword <=", value, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordIn(List<String> values) {
            addCriterion("encryptedPassword in", values, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordNotIn(List<String> values) {
            addCriterion("encryptedPassword not in", values, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordBetween(String value1, String value2) {
            addCriterion("encryptedPassword between", value1, value2, "encryptedpassword");
            return (Criteria) this;
        }

        public Criteria andEncryptedpasswordNotBetween(String value1, String value2) {
            addCriterion("encryptedPassword not between", value1, value2, "encryptedpassword");
            return (Criteria) this;
        }
		public Criteria andNameIsNull() {
            addCriterion("name is null");
            return (Criteria) this;
        }
        
         public Criteria andNameIsNotNull() {
            addCriterion("name is not null");
            return (Criteria) this;
        }
        
         public Criteria andNameEqualTo(String value) {
            addCriterion("name =", value, "name");
            return (Criteria) this;
        }
        
         public Criteria andNameNotEqualTo(String value) {
            addCriterion("name <>", value, "name");
            return (Criteria) this;
        }
        
        public Criteria andNameGreaterThan(Long value) {
            addCriterion("name >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("name >=", value, "name");
            return (Criteria) this;
        }
        
         public Criteria andNameLessThan(String value) {
            addCriterion("name <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("name <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("name in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("name not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("name between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("name not between", value1, value2, "name");
            return (Criteria) this;
        }
		public Criteria andEmailIsNull() {
            addCriterion("email is null");
            return (Criteria) this;
        }
        
         public Criteria andEmailIsNotNull() {
            addCriterion("email is not null");
            return (Criteria) this;
        }
        
         public Criteria andEmailEqualTo(String value) {
            addCriterion("email =", value, "email");
            return (Criteria) this;
        }
        
         public Criteria andEmailNotEqualTo(String value) {
            addCriterion("email <>", value, "email");
            return (Criteria) this;
        }
        
        public Criteria andEmailGreaterThan(Long value) {
            addCriterion("email >", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailGreaterThanOrEqualTo(String value) {
            addCriterion("email >=", value, "email");
            return (Criteria) this;
        }
        
         public Criteria andEmailLessThan(String value) {
            addCriterion("email <", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailLessThanOrEqualTo(String value) {
            addCriterion("email <=", value, "email");
            return (Criteria) this;
        }

        public Criteria andEmailIn(List<String> values) {
            addCriterion("email in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotIn(List<String> values) {
            addCriterion("email not in", values, "email");
            return (Criteria) this;
        }

        public Criteria andEmailBetween(String value1, String value2) {
            addCriterion("email between", value1, value2, "email");
            return (Criteria) this;
        }

        public Criteria andEmailNotBetween(String value1, String value2) {
            addCriterion("email not between", value1, value2, "email");
            return (Criteria) this;
        }
		public Criteria andCreationdateIsNull() {
            addCriterion("creationDate is null");
            return (Criteria) this;
        }
        
         public Criteria andCreationdateIsNotNull() {
            addCriterion("creationDate is not null");
            return (Criteria) this;
        }
        
         public Criteria andCreationdateEqualTo(String value) {
            addCriterion("creationDate =", value, "creationdate");
            return (Criteria) this;
        }
        
         public Criteria andCreationdateNotEqualTo(String value) {
            addCriterion("creationDate <>", value, "creationdate");
            return (Criteria) this;
        }
        
        public Criteria andCreationdateGreaterThan(Long value) {
            addCriterion("creationDate >", value, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateGreaterThanOrEqualTo(String value) {
            addCriterion("creationDate >=", value, "creationdate");
            return (Criteria) this;
        }
        
         public Criteria andCreationdateLessThan(String value) {
            addCriterion("creationDate <", value, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateLessThanOrEqualTo(String value) {
            addCriterion("creationDate <=", value, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateIn(List<String> values) {
            addCriterion("creationDate in", values, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateNotIn(List<String> values) {
            addCriterion("creationDate not in", values, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateBetween(String value1, String value2) {
            addCriterion("creationDate between", value1, value2, "creationdate");
            return (Criteria) this;
        }

        public Criteria andCreationdateNotBetween(String value1, String value2) {
            addCriterion("creationDate not between", value1, value2, "creationdate");
            return (Criteria) this;
        }
		public Criteria andModificationdateIsNull() {
            addCriterion("modificationDate is null");
            return (Criteria) this;
        }
        
         public Criteria andModificationdateIsNotNull() {
            addCriterion("modificationDate is not null");
            return (Criteria) this;
        }
        
         public Criteria andModificationdateEqualTo(String value) {
            addCriterion("modificationDate =", value, "modificationdate");
            return (Criteria) this;
        }
        
         public Criteria andModificationdateNotEqualTo(String value) {
            addCriterion("modificationDate <>", value, "modificationdate");
            return (Criteria) this;
        }
        
        public Criteria andModificationdateGreaterThan(Long value) {
            addCriterion("modificationDate >", value, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateGreaterThanOrEqualTo(String value) {
            addCriterion("modificationDate >=", value, "modificationdate");
            return (Criteria) this;
        }
        
         public Criteria andModificationdateLessThan(String value) {
            addCriterion("modificationDate <", value, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateLessThanOrEqualTo(String value) {
            addCriterion("modificationDate <=", value, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateIn(List<String> values) {
            addCriterion("modificationDate in", values, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateNotIn(List<String> values) {
            addCriterion("modificationDate not in", values, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateBetween(String value1, String value2) {
            addCriterion("modificationDate between", value1, value2, "modificationdate");
            return (Criteria) this;
        }

        public Criteria andModificationdateNotBetween(String value1, String value2) {
            addCriterion("modificationDate not between", value1, value2, "modificationdate");
            return (Criteria) this;
        }
		public Criteria andOpTimeIsNull() {
            addCriterion("op_time is null");
            return (Criteria) this;
        }
        
         public Criteria andOpTimeIsNotNull() {
            addCriterion("op_time is not null");
            return (Criteria) this;
        }
        
         public Criteria andOpTimeEqualTo(Date value) {
            addCriterion("op_time =", value, "opTime");
            return (Criteria) this;
        }
        
         public Criteria andOpTimeNotEqualTo(Date value) {
            addCriterion("op_time <>", value, "opTime");
            return (Criteria) this;
        }
        
        public Criteria andOpTimeGreaterThan(Long value) {
            addCriterion("op_time >", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("op_time >=", value, "opTime");
            return (Criteria) this;
        }
        
         public Criteria andOpTimeLessThan(Date value) {
            addCriterion("op_time <", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeLessThanOrEqualTo(Date value) {
            addCriterion("op_time <=", value, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeIn(List<Date> values) {
            addCriterion("op_time in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotIn(List<Date> values) {
            addCriterion("op_time not in", values, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeBetween(Date value1, Date value2) {
            addCriterion("op_time between", value1, value2, "opTime");
            return (Criteria) this;
        }

        public Criteria andOpTimeNotBetween(Date value1, Date value2) {
            addCriterion("op_time not between", value1, value2, "opTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}

