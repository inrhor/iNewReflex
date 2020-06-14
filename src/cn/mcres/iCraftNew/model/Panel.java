package cn.mcres.iCraftNew.model;

import java.util.List;

public class Panel {
	private String id;
	private List<Integer> backSlots;
	private List<Integer> buttonSlots;
	private List<Integer> closeSlots;
	private List<Integer> matrixSlots;
	private List<Integer> resultSlots;
	/**
	 * @param id 合成栏id
	 */
	public Panel(String id, List<Integer> buttonSlots, List<Integer> closeSlots, List<Integer> matrixSlots, List<Integer> resultSlots, List<Integer> backSlots) {
		this.id = id;
		this.buttonSlots = buttonSlots;
		this.closeSlots = closeSlots;
		this.matrixSlots = matrixSlots;
		this.resultSlots = resultSlots;
		this.backSlots = backSlots;
	}
	/**
	 * @return 合成栏id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * @param slots 合成按钮位置索引
	 */
	public void setButtonSlots(List<Integer> slots) {
		this.buttonSlots = slots;
	}
	/**
	 * @return 合成按钮位置索引
	 */
	public List<Integer> getButtonSlots() {
		return this.buttonSlots;
	}
	/**
	 * @param matrix 放置所需物品的位置索引
	 */
	public void setMatrix(List<Integer> matrix) {
		this.matrixSlots = matrix;
	}
	/**
	 * @return 放置所需物品的位置索引
	 */
	public List<Integer> getMatrixSlots() {
		return this.matrixSlots;
	}
	/**
	 * @param slots 关闭按钮位置索引
	 */
	public void setCloseSlots(List<Integer> slots) {
		this.closeSlots = slots;
	}
	/**
	 * @return 关闭按钮位置索引
	 */
	public List<Integer> getCloseSlot() {
		return this.closeSlots;
	}
	/**
	 * @param slots 合成结果位置索引
	 */
	public void setResultsSlots(List<Integer> slots) {
		this.resultSlots = slots;
	}
	/**
	 * @return 合成结果位置索引
	 */
	public List<Integer> getResultsSlots() {
		return this.resultSlots;
	}

	public void setBackSlots(List<Integer> backSlots) {
		this.backSlots = backSlots;
	}

	public List<Integer> getBackSlots() {
		return backSlots;
	}
}
