package com.example.botsandroid;

public class Achievements {
	private int[] ids = { 
			R.id.achievement_beginner, R.id.achievement_beginner_detail,
			R.id.achievement_retreat, R.id.achievement_retreat_detail,
			R.id.achievement_coinseeker, R.id.achievement_coinseeker_detail,
			R.id.achievement_levelbachelor, R.id.achievement_levelbachelor_detail,
			R.id.achievement_coinfinder, R.id.achievement_coinfinder_detail,
			R.id.achievement_skinlover, R.id.achievement_skinlover_detail,
			R.id.achievement_coincollector, R.id.achievement_coincollector_detail,
			R.id.achievement_levelmaster, R.id.achievement_levelmaster_detail,
			R.id.achievement_coinvacuum, R.id.achievement_coinvacuum_detail,
			R.id.achievement_coinmagnet, R.id.achievement_coinmagnet_detail,
			R.id.achievement_skin2lover, R.id.achievement_skin2lover_detail,
			R.id.achievement_levelphd, R.id.achievement_levelphd_detail,
			R.id.achievement_coindealer, R.id.achievement_coindealer_detail,
			R.id.achievement_coinspecialist, R.id.achievement_coinspecialist_detail,
			R.id.achievement_levelpostdoc, R.id.achievement_levelpostdoc_detail,
			R.id.achievement_skin3lover, R.id.achievement_skin3lover_detail,
			R.id.achievement_coinbroker, R.id.achievement_coinbroker_detail,
			R.id.achievement_coinprofessor, R.id.achievement_coinprofessor_detail,
			R.id.achievement_skin4lover, R.id.achievement_skin4lover_detail,
			R.id.achievement_levelprofessor, R.id.achievement_levelprofessor_detail,
			R.id.achievement_coinmaster, R.id.achievement_coinmaster_detail,
			R.id.achievement_skin5lover, R.id.achievement_skin5lover_detail,
			R.id.achievement_coinfanatic, R.id.achievement_coinfanatic_detail,
			R.id.achievement_coinmaniac, R.id.achievement_coinmaniac_detail };
	
	private int[] names = { 
			R.string.achievement_beginner,
			R.string.achievement_retreat,
			R.string.achievement_coinseeker,
			R.string.achievement_levelbachelor,
			R.string.achievement_coinfinder,
			R.string.achievement_skinlover,
			R.string.achievement_coincollector,
			R.string.achievement_levelmaster,
			R.string.achievement_coinvacuum,
			R.string.achievement_coinmagnet,
			R.string.achievement_skin2lover,
			R.string.achievement_levelphd,
			R.string.achievement_coindealer,
			R.string.achievement_coinspecialist,
			R.string.achievement_levelpostdoc,
			R.string.achievement_skin3lover,
			R.string.achievement_coinbroker,
			R.string.achievement_coinprofessor,
			R.string.achievement_skin4lover,
			R.string.achievement_levelprofessor,
			R.string.achievement_coinmaster,
			R.string.achievement_skin5lover,
			R.string.achievement_coinfanatic,
			R.string.achievement_coinmaniac };
	
	private int[] rewards = { 10, 10, 15, 15, 20, 20, 25, 25, 30, 30, 35, 35, 40, 40, 45, 45, 50, 50, 55, 55, 60, 60, 65, 65 };
	
	public int[] getIds() {
		return ids;
	}
	
	public int[] getNames() {
		return names;
	}
	
	public int[] getRewards() {
		return rewards;
	}
}
