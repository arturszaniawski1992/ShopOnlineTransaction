package searchcriteria;

public class EmployeeSearchCriteria {
	private Long departmentId;
	private Long attendantId;
	private Long positionId;

	public EmployeeSearchCriteria() {
		super();
	}

	public EmployeeSearchCriteria(Long departmentId, Long attendantId, Long positionId) {
		super();
		this.departmentId = departmentId;
		this.attendantId = attendantId;
		this.positionId = positionId;
	}

	public Long getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}

	public Long getAttendantId() {
		return attendantId;
	}

	public void setAttendantId(Long attendantId) {
		this.attendantId = attendantId;
	}

	public Long getPositionId() {
		return positionId;
	}

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

}
