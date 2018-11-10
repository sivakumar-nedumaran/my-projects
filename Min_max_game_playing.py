import time
import bisect
from collections import OrderedDict
memoization = {}
start_time = time.time()
b = p = 0
apps_cluster = OrderedDict()
lahsa_picked = []
spla_picked = []
apps_dictionary = []


class GameState:

	def __init__(self, spla_occupancy, lahsa_occupancy):
		self.spla_occupancy = spla_occupancy
		self.lahsa_occupancy = lahsa_occupancy

	def next_state(self, req, turn):
		# print "Turn of " + turn + " : picks - " + str(req[0])
		if turn == "spla":
			new_occupancy = list(self.spla_occupancy)
			for i in range(2, 9):
				new_occupancy[i-2] += req[i]
			return GameState(new_occupancy, self.lahsa_occupancy)
		elif turn == "lahsa":
			new_occupancy = list(self.lahsa_occupancy)
			for i in range(2, 9):
				new_occupancy[i-2] += req[i]
			return GameState(self.spla_occupancy, new_occupancy)

	def can_accommodate(self, req, turn):
		global p, b
		if turn == "spla":
			for i in range(2, 9):
				if int(self.spla_occupancy[i-2]) + int(req[i]) > p:
					return False
		elif turn == "lahsa":
			for i in range(2, 9):
				if int(self.lahsa_occupancy[i-2]) + int(req[i]) > b:
					return False
		return True

	def get_score(self):
		global p, b
		score = float((int(sum(self.spla_occupancy))/float(p*7))*100), float((int(sum(self.lahsa_occupancy))/float(b*7))*100)
		return score

	def print_state(self):
		print
		print "spla_occupancy = " + str(self.spla_occupancy)
		print "lahsa_occupancy = " + str(self.lahsa_occupancy)
		self.get_score()
		print


def read_input():
	global b, p, apps_cluster, apps_dictionary
	spla_count = lahsa_count = applicants_count = 0
	spla_selected = []
	lahsa_selected = []
	spla_occupancy = [0]*7
	lahsa_occupancy = [0]*7
	with open("input.txt", "r") as input_file:
		input_data = input_file.read().splitlines()
		b = int(input_data[0])
		p = int(input_data[1])
		lahsa_count = int(input_data[2])
		for line in range(3, 3+lahsa_count):
			lahsa_selected.append(input_data[line])
		spla_count = int(input_data[3+lahsa_count])
		for line in range(4+lahsa_count, 4+lahsa_count+spla_count):
			spla_selected.append(input_data[line])
		applicants_count = int(input_data[4+lahsa_count+spla_count])
		for line_no in range(5+lahsa_count+spla_count, 5+lahsa_count+spla_count+applicants_count):
			line = input_data[line_no]
			app_id = line[:5]
			if app_id in lahsa_selected:
				for i in range(0, 7):
					lahsa_occupancy[i] = lahsa_occupancy[i] + int(line[i+13])
			elif app_id in spla_selected:
				for i in range(0, 7):
					spla_occupancy[i] = spla_occupancy[i] + int(line[i+13])
			elif line[5] == 'F' and int(line[6:9]) > 17 and line[9] == 'N':
				if line[10:13] == "NYY":
					apps_dictionary.append([app_id, "C", int(line[13]), int(line[14]), int(line[15]), int(line[16]), int(line[17]), int(line[18]), int(line[19]), "O"])
				else:
					apps_dictionary.append([app_id, "L", int(line[13]), int(line[14]), int(line[15]), int(line[16]), int(line[17]), int(line[18]), int(line[19]), "O"])
			elif line[10:13] == "NYY":
				apps_dictionary.append([app_id, "S", int(line[13]), int(line[14]), int(line[15]), int(line[16]), int(line[17]), int(line[18]), int(line[19]), "O"])
		apps_dictionary.sort()
		for appln in apps_dictionary:
			if apps_cluster.get(tuple(appln[1:9]), None):
				apps_cluster[tuple(appln[1:9])][appln[0]] = 'O'
			else:
				apps_cluster[tuple(appln[1:9])] = OrderedDict()
				apps_cluster[tuple(appln[1:9])][appln[0]] = 'O'
		return GameState(spla_occupancy, lahsa_occupancy)


def mini_max(game_state):
	global spla_picked, lahsa_picked, apps_cluster
	best_move = []
	best_score = float('-inf')
	for requirement, applications in apps_cluster.items():
		if requirement[0] in ['C', 'S']:
			for application in applications.items():
				if application[1] == "O" and game_state.can_accommodate(list((application[0],) + requirement), "spla"):
					clone = game_state.next_state(list((application[0],) + requirement), "spla")
					apps_cluster[requirement][application[0]] = 'S'
					bisect.insort(spla_picked, application[0])
					spla_score, lahsa_score = min_play(clone)
					if spla_score > best_score:
						best_move = application[0]
						best_score = spla_score
						if best_score == 100.0:
							return best_move, best_score
					apps_cluster[requirement][application[0]] = 'O'
					del spla_picked[bisect.bisect_left(spla_picked, application[0])]
					break
	return best_move, best_score


def spla_play(game_state):
	global spla_picked, lahsa_picked, memoization, apps_cluster
	key = len(spla_picked) + len(lahsa_picked)
	tuple2 = (tuple(spla_picked), tuple(lahsa_picked))
	if memoization.get(key, None):
		if memoization[key].get(tuple2):
			return memoization[key][tuple2][0], memoization[key][tuple2][1], False
	best_score = float('-inf')
	opp_score = float('-inf')
	flag = 0
	set_exception = 0
	first_item = False
	for requirement, applications in apps_cluster.items():
		if requirement[0] in ['C', 'S']:
			for application in applications.items():
				if application[1] == "O" and game_state.can_accommodate(list((application[0],) + requirement), "spla"):
					if first_item is False and flag == 0:
						first_item = True
					elif first_item is True and flag == 1:
						first_item = False
					flag = 1
					clone = game_state.next_state(list((application[0],) + requirement), "spla")
					apps_cluster[requirement][application[0]] = 'S'
					bisect.insort(spla_picked, application[0])
					spla_score, lahsa_score, next_first_item = spla_play(clone)
					if next_first_item is True and next_first_item == first_item:
						set_exception = 1
					if spla_score > best_score:
						opp_score = lahsa_score
						best_score = spla_score
						if best_score == 100.0:
							set_exception = 1
					apps_cluster[requirement][application[0]] = 'O'
					del spla_picked[bisect.bisect_left(spla_picked, application[0])]
					break
		if set_exception == 1:
			break
	if flag == 0:
		best_score, opp_score = game_state.get_score()
		first_item = True
	if memoization.get(key, None) is None:
		memoization[key] = {}
	memoization[key].update({tuple2: [best_score, opp_score]})
	return best_score, opp_score, first_item


def lahsa_play(game_state):
	global lahsa_picked, memoization, spla_picked, apps_cluster
	key = len(spla_picked) + len(lahsa_picked)
	tuple2 = (tuple(spla_picked), tuple(lahsa_picked))
	if memoization.get(key, None):
		if memoization[key].get(tuple2, None):
			return memoization[key][tuple2][0], memoization[key][tuple2][1], False
	best_score = float('-inf')
	opp_score = float('-inf')
	flag = 0
	set_exception = 0
	first_item = False
	for requirement, applications in apps_cluster.items():
		if requirement[0] in ['C', 'L']:
			for application in applications.items():
				if application[1] == "O" and game_state.can_accommodate(list((application[0],) + requirement), "lahsa"):
					if first_item is False and flag == 0:
						first_item = True
					elif first_item is True and flag == 1:
						first_item = False
					flag = 1
					clone = game_state.next_state(list((application[0],) + requirement), "lahsa")
					apps_cluster[requirement][application[0]] = 'L'
					bisect.insort(lahsa_picked, application[0])
					spla_score, lahsa_score, next_first_item = lahsa_play(clone)
					if next_first_item is True and next_first_item == first_item:
						set_exception = 1
					if lahsa_score > best_score:
						opp_score = spla_score
						best_score = lahsa_score
						if best_score == 100.0:
							set_exception = 1
					apps_cluster[requirement][application[0]] = 'O'
					del lahsa_picked[bisect.bisect_left(lahsa_picked, application[0])]
					break
		if set_exception == 1:
			break
	if flag == 0:
		opp_score, best_score = game_state.get_score()
		first_item = True
	if memoization.get(key, None) is None:
		memoization[key] = {}
	memoization[key].update({tuple2: [opp_score, best_score]})
	return opp_score, best_score, first_item


def min_play(game_state):
	global lahsa_picked, spla_picked, memoization, apps_cluster
	key = len(spla_picked) + len(lahsa_picked)
	tuple2 = (tuple(spla_picked), tuple(lahsa_picked))
	if memoization.get(key, None):
		if memoization[key].get(tuple2, None):
			return memoization[key][tuple2][0], memoization[key][tuple2][1]
	best_score = float('-inf')
	opp_score = float('-inf')
	flag = 0
	set_exception = 0
	for requirement, applications in apps_cluster.items():
		if requirement[0] in ['C', 'L']:
			for application in applications.items():
				if application[1] == "O" and game_state.can_accommodate(list((application[0],) + requirement), "lahsa"):
					flag = 1
					clone = game_state.next_state(list((application[0],) + requirement), "lahsa")
					apps_cluster[requirement][application[0]] = 'L'
					bisect.insort(lahsa_picked, application[0])
					spla_score, lahsa_score = max_play(clone)
					if lahsa_score > best_score:
						opp_score = spla_score
						best_score = lahsa_score
						if best_score == 100.0:
							set_exception = 1
					apps_cluster[requirement][application[0]] = 'O'
					del lahsa_picked[bisect.bisect_left(lahsa_picked, application[0])]
					break
		if set_exception == 1:
			break
	if flag == 0:
		opp_score, best_score, dummy = spla_play(game_state)
	if memoization.get(key, None) is None:
		memoization[key] = {}
	memoization[key].update({tuple2: [opp_score, best_score]})
	return opp_score, best_score


def max_play(game_state):
	global spla_picked, lahsa_picked, memoization, apps_cluster
	key = len(spla_picked) + len(lahsa_picked)
	tuple2 = (tuple(spla_picked), tuple(lahsa_picked))
	if memoization.get(key, None):
		if memoization[key].get(tuple2, None):
			return memoization[key][tuple2][0], memoization[key][tuple2][1]
	best_score = float('-inf')
	opp_score = float('-inf')
	flag = 0
	set_exception = 0
	for requirement, applications in apps_cluster.items():
		if requirement[0] in ['C', 'S']:
			for application in applications.items():
				if application[1] == "O" and game_state.can_accommodate(list((application[0],) + requirement), "spla"):
					flag = 1
					clone = game_state.next_state(list((application[0],) + requirement), "spla")
					apps_cluster[requirement][application[0]] = 'S'
					bisect.insort(spla_picked, application[0])
					spla_score, lahsa_score = min_play(clone)
					if spla_score > best_score:
						opp_score = lahsa_score
						best_score = spla_score
						if best_score == 100.0:
							set_exception = 1
					apps_cluster[requirement][application[0]] = 'O'
					del spla_picked[bisect.bisect_left(spla_picked, application[0])]
					break
		if set_exception == 1:
			break
	if flag == 0:
		best_score, opp_score, dummy = lahsa_play(game_state)
	if memoization.get(key, None) is None:
		memoization[key] = {}
	memoization[key].update({tuple2: [best_score, opp_score]})
	return best_score, opp_score


start_state = read_input()
# start_state.print_state()

ans = mini_max(start_state)
# print ans[0]

with open("output.txt", "w") as output:
	output.write(ans[0])

# print "Time taken = " + str(time.time() - start_time)
