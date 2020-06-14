package cn.mcres.iCraftNew.model;

import java.util.List;

import org.bukkit.inventory.ItemStack;

public class Recipe {
	private String id;
	private String panelId;
	private List<ItemStack> itemMatrix;
	private List<ItemStack> results;
	/**
	 * @param id 配方id
	 * @param panelID 合成栏id
	 */
	public Recipe(String id,String panelID, List<ItemStack> itemMatrix, List<ItemStack> results) {
		this.id = id;
		this.panelId = panelID;
		this.itemMatrix = itemMatrix;
		this.results = results;
	}
	/**
	 * @return 合成栏id
	 */
	public String getPanelId() {
		return this.panelId;
	}
	/**
	 * @return 配方id
	 */
	public String getId() {
		return this.id;
	}
	/**
	 * @param matrix 所需物品
	 */
	public void setItemMatrix(List<ItemStack> matrix) {
		this.itemMatrix = matrix;
	}
	/**
	 * @return 所需物品
	 */
	public List<ItemStack> getItemMatrix() {
		return this.itemMatrix;
	}
	/**
	 * @param results 合成结果
	 */
	public void setResults(List<ItemStack> results) {
		this.results = results;
	}
	/**
	 * @return 合成结果
	 */
	public List<ItemStack> getResults(){
		return this.results;
	}
}
